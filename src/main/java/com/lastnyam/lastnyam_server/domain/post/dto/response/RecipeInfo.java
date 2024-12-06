package com.lastnyam.lastnyam_server.domain.post.dto.response;

import com.lastnyam.lastnyam_server.domain.post.domain.RecipeAuthor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeInfo {
    private String recipe;
    private byte[] recipeImage;
    private RecipeAuthor author;
}
