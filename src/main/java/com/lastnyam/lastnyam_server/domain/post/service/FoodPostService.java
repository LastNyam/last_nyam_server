package com.lastnyam.lastnyam_server.domain.post.service;

import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.post.domain.*;
import com.lastnyam.lastnyam_server.domain.post.dto.request.UpdatePostStatusRequest;
import com.lastnyam.lastnyam_server.domain.post.dto.request.UploadFoodRequest;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostDetailInfo;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostInfo;
import com.lastnyam.lastnyam_server.domain.post.dto.response.PostInfoWithPosition;
import com.lastnyam.lastnyam_server.domain.post.dto.response.RecipeInfo;
import com.lastnyam.lastnyam_server.domain.post.repository.FoodCategoryRepository;
import com.lastnyam.lastnyam_server.domain.post.repository.FoodPostRepository;
import com.lastnyam.lastnyam_server.domain.post.repository.RecommendRecipeRepository;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodPostService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final FoodPostRepository foodPostRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final RecommendRecipeRepository recipeRepository;

    @Transactional
    public void uploadFoodPost(UploadFoodRequest request, Long userId) {
        byte[] foodImage = null;
        try {
            foodImage = request.getFoodImage().getBytes();
        } catch (IOException e) {
            throw new ServiceException(ExceptionCode.FILE_IO_EXCEPTION);
        }

        byte[] recipeImage = null;
        if (request.getRecipeImage() != null) {
            try {
                recipeImage = request.getFoodImage().getBytes();
            } catch (IOException e) {
                throw new ServiceException(ExceptionCode.FILE_IO_EXCEPTION);
            }
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
                .image(foodImage)
                .reservationTimeLimit(request.getReservationTime())
                .build();

        FoodPost savedPost = foodPostRepository.save(newPost);

        if (request.getRecipe() != null) {
            RecommendRecipe recipe = RecommendRecipe.builder()
                    .foodPost(savedPost)
                    .recipe(request.getRecipe())
                    .author(RecipeAuthor.OWNER)
                    .image(recipeImage)
                    .build();

            recipeRepository.save(recipe);
        }

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
                .map(post ->
                        PostInfo.builder()
                                .foodId(post.getId())
                                .foodCategory(post.getCategory().getName())
                                .foodName(post.getFoodName())
                                .originPrice(post.getOriginPrice())
                                .discountPrice(post.getDiscountPrice())
                                .endTime(post.getEndTime())
                                .status(post.getStatus())
                                .image(post.getImage())
                                .build()
                ).toList();
    }

    @Transactional
    public void updatePostStatus(UpdatePostStatusRequest request, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        FoodPost savedFoodPost = foodPostRepository.findById(request.getFoodId())
                .orElseThrow(() -> new ServiceException(ExceptionCode.FOOD_POST_NOT_FOUND));

        if (!savedFoodPost.getStore().equals(savedUser.getStore())) {
            throw new ServiceException(ExceptionCode.UN_AUTHENTICATION);
        }

        savedFoodPost.setStatus(this.convertStatus(request.getStatus()));
    }

    private PostStatus convertStatus(String status) {
        return Arrays.stream(PostStatus.values())
                .filter(postStatus -> postStatus.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new ServiceException(ExceptionCode.INVALID_POST_STATUS));
    }

    @Transactional(readOnly = true)
    public PostDetailInfo getPostDetailInfo(Long foodId) {
        FoodPost savedFoodPost = foodPostRepository.findById(foodId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.FOOD_POST_NOT_FOUND));

        List<RecommendRecipe> recipes = recipeRepository.findAllByFoodPost(savedFoodPost);

        return PostDetailInfo.builder()
                .storeId(savedFoodPost.getStore().getId())
                .storeName(savedFoodPost.getStore().getName())
                .foodName(savedFoodPost.getFoodName())
                .content(savedFoodPost.getContent())
                .originPrice(savedFoodPost.getOriginPrice())
                .discountPrice(savedFoodPost.getDiscountPrice())
                .endTime(savedFoodPost.getEndTime())
                .count(savedFoodPost.getCount())
                .reservationTime(savedFoodPost.getReservationTimeLimit())
                .status(savedFoodPost.getStatus())
                .image(savedFoodPost.getImage())
                .recommendRecipe(this.convertRecipeInfo(recipes))
                .build();
    }

    private List<RecipeInfo> convertRecipeInfo(List<RecommendRecipe> recipes) {
        return recipes.stream()
                .map(recipe -> RecipeInfo.builder()
                        .recipe(recipe.getRecipe())
                        .recipeImage(recipe.getImage())
                        .author(recipe.getAuthor())
                        .build())
                .toList();
    }

    public List<PostInfoWithPosition> getFoodPostList() {
        return foodPostRepository.findAll().stream()
                .map(post -> PostInfoWithPosition.builder()




                        .build())
                .toList();
    }
}
