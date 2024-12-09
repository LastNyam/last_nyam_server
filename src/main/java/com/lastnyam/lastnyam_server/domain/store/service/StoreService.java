package com.lastnyam.lastnyam_server.domain.store.service;

import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.domain.RecipeAuthor;
import com.lastnyam.lastnyam_server.domain.post.domain.RecommendRecipe;
import com.lastnyam.lastnyam_server.domain.post.repository.FoodPostRepository;
import com.lastnyam.lastnyam_server.domain.post.repository.RecommendRecipeRepository;
import com.lastnyam.lastnyam_server.domain.store.domain.RatingToTemperature;
import com.lastnyam.lastnyam_server.domain.store.domain.Review;
import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import com.lastnyam.lastnyam_server.domain.store.dto.request.RegisterStoreRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.request.UpdateStoreAddressRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.request.UploadReviewRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.response.ReviewInfo;
import com.lastnyam.lastnyam_server.domain.store.dto.response.StoreInfo;
import com.lastnyam.lastnyam_server.domain.store.repository.ReviewRepository;
import com.lastnyam.lastnyam_server.domain.store.repository.StoreRepository;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final FoodPostRepository foodPostRepository;
    private final RecommendRecipeRepository recommendRecipeRepository;

    @Transactional
    public void registerStore(RegisterStoreRequest request, Long userId) {
        Owner savedOwner = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        // 테스트를 위해 중복 허용
//        storeRepository.findByBusinessNumber(request.getBusinessNumber())
//                .ifPresent(store -> {
//                    throw new ServiceException(ExceptionCode.DUPLICATED_BUSINESS_NUMBER);
//                });

        byte[] byteFile = null;
        if (request.getStoreImage() != null) {
            try {
                byteFile = request.getStoreImage().getBytes();
            } catch (IOException e) {
                log.error("profileImage upload error: {}", e.getMessage());
                throw new ServiceException(ExceptionCode.FILE_IO_EXCEPTION);
            }
        }

        Store newStore = Store.builder()
                .name(request.getStoreName())
                .businessNumber(request.getBusinessNumber())
                .image(byteFile)
                .positionX(request.getPosX())
                .positionY(request.getPosY())
                .contactNumber(request.getCallNumber())
                .address(request.getAddress())
                .build();

        Store savedStore = storeRepository.save(newStore);
        savedOwner.setStore(savedStore);
    }

    @Transactional
    public void updateStoreName(String storeName, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store userStore = savedUser.getStore();

        userStore.setName(storeName);
    }

    @Transactional
    public void updateStoreAddress(UpdateStoreAddressRequest request, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store userStore = savedUser.getStore();

        userStore.setAddress(request.getAddress());
        userStore.setPositionX(request.getPosX());
        userStore.setPositionY(request.getPosY());
    }

    @Transactional
    public void updateStoreContactNumber(String callNumber, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store userStore = savedUser.getStore();

        userStore.setContactNumber(callNumber);
    }

    @Transactional(readOnly = true)
    public StoreInfo getMyStoreInfo(Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store myStore = savedUser.getStore();

        if (myStore == null) {
            throw new ServiceException(ExceptionCode.STORE_NOT_FOUND);
        }

        return this.convertStoreInfo(myStore);
    }

    private StoreInfo convertStoreInfo(Store store) {
        return StoreInfo.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .storeImage(store.getImage())
                .callNumber(store.getContactNumber())
                .address(store.getAddress())
                .temperature(store.getTemperature())
                .build();
    }

    @Transactional
    public void uploadReview(UploadReviewRequest request, Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store savedStore = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ServiceException(ExceptionCode.STORE_NOT_FOUND));

        FoodPost savedFoodPost = foodPostRepository.findById(request.getFoodId())
                .orElseThrow(() -> new ServiceException(ExceptionCode.FOOD_POST_NOT_FOUND));

        // 리뷰 만들기, 가게 매너온도 변경
        Review newReview = Review.builder()
                .store(savedStore)
                .user(savedUser)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        reviewRepository.save(newReview);

        storeRepository.updateTemperature(savedStore.getId(), RatingToTemperature.getTemperature(request.getRating()));

        // 레시피 만들기
        if (!isNullOrEmpty(request.getRecipe())) {
            RecommendRecipe newRecipe = RecommendRecipe.builder()
                    .foodPost(savedFoodPost)
                    .recipe(request.getRecipe())
                    .author(RecipeAuthor.USER)
                    .build();

            recommendRecipeRepository.save(newRecipe);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    @Transactional(readOnly = true)
    public List<ReviewInfo> getReviewList(Long userId) {
        Owner savedOwner = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        List<Review> reviews = reviewRepository.findAllByStore(savedOwner.getStore());

        return reviews.stream()
                .map(review -> ReviewInfo.builder()
                        .userNickname(review.getUser().getNickname())
                        .rating(review.getRating())
                        .content(review.getContent())
                        .build())
                .toList();
    }
}
