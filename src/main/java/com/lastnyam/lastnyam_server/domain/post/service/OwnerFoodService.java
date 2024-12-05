package com.lastnyam.lastnyam_server.domain.post.service;

import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.post.domain.FoodCategory;
import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.post.dto.request.UploadFoodRequest;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostInfo;
import com.lastnyam.lastnyam_server.domain.post.repository.FoodCategoryRepository;
import com.lastnyam.lastnyam_server.domain.post.repository.FoodPostRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerFoodService {
    private final OwnerRepository ownerRepository;
    private final FoodPostRepository foodPostRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    @Transactional
    public void uploadFoodPost(UploadFoodRequest request, Long userId) {
        byte[] image = null;
        try {
            image = request.getImage().getBytes();
        } catch (IOException e) {
            throw new ServiceException(ExceptionCode.FILE_IO_EXCEPTION);
        }

        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        FoodCategory category = foodCategoryRepository.findByName(request.getFoodCategory())
                .orElseThrow(() -> new ServiceException(ExceptionCode.FOOD_CATEGORY_NOT_FOUND));

        FoodPost newPost = FoodPost.builder()
                .store(savedUser.getStore())
                .category(category)
                .foodName(request.getFoodName())
                .content(request.getContent())
                .originPrice(request.getOriginPrice())
                .discountPrice(request.getDiscountPrice())
                .endTime(request.getEndTime())
                .count(request.getCount())
                .image(image)
                .build();

        foodPostRepository.save(newPost);

        // TODO. 관심매장 알림
    }

    @Transactional(readOnly = true)
    public List<PostInfo> getMyFoodPost(Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        List<FoodPost> foodPosts = foodPostRepository.findAllByStore(savedUser.getStore());

        return this.convertPostInfo(foodPosts);
    }

    private List<PostInfo> convertPostInfo(List<FoodPost> foodPosts) {
        return foodPosts.stream()
                .map(foodPost ->
                        PostInfo.builder()
                                .storeId(foodPost.getStore().getId())
                                .storeName(foodPost.getStore().getName())
                                .foodId(foodPost.getId())
                                .foodCategory(foodPost.getCategory().getName())
                                .foodName(foodPost.getFoodName())
                                .originPrice(foodPost.getOriginPrice())
                                .discountPrice(foodPost.getDiscountPrice())
                                .endTime(foodPost.getEndTime())
                                .image(foodPost.getImage())
                                .build()
                ).toList();
    }
}
