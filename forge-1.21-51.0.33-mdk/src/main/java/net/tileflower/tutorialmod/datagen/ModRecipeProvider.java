package net.tileflower.tutorialmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.tileflower.tutorialmod.TutorialMod;
import net.tileflower.tutorialmod.block.ModBlocks;
import net.tileflower.tutorialmod.item.Moditems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> ALEXANDRITE_SMELTABLES = List.of(Moditems.RAW_ALEXANDRITE.get(),ModBlocks.ALEXANDRITE_BLOCK.get(),ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA") //nine blocks have a shortcut!
                .pattern("AAA")
                .define('A', Moditems.ALEXANDRITE.get())
                .unlockedBy(getHasName(Moditems.ALEXANDRITE.get()),has(Moditems.ALEXANDRITE.get()))
                .save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Moditems.ALEXANDRITE.get(),9)
                .requires(ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ALEXANDRITE_BLOCK.get()),has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(pRecipeOutput);

          ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Moditems.ALEXANDRITE.get(),32)
                .requires(ModBlocks.MAGIC_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ALEXANDRITE_BLOCK.get()),has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                  .save(pRecipeOutput, TutorialMod.MOD_ID+":alexandrite_from_magic_block");
          oreSmelting(pRecipeOutput,ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, Moditems.ALEXANDRITE.get(), 0.25f,200,"alexandrite");
          oreBlasting(pRecipeOutput,ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, Moditems.ALEXANDRITE.get(), 0.25f,100,"alexandrite");

          stairBuilder(ModBlocks.ALEXANDRITE_STAIRS.get(),Ingredient.of(Moditems.ALEXANDRITE.get())).group("alexandrite")
                  .unlockedBy(getHasName(Moditems.ALEXANDRITE.get()),has(Moditems.ALEXANDRITE.get())).save(pRecipeOutput);
        slab(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ALEXANDRITE_SLAB.get(), Moditems.ALEXANDRITE.get());

        buttonBuilder(ModBlocks.ALEXANDRITE_BUTTON.get(), Ingredient.of(Moditems.ALEXANDRITE.get())).group("alexandrite")
                .unlockedBy(getHasName(Moditems.ALEXANDRITE.get()), has(Moditems.ALEXANDRITE.get())).save(pRecipeOutput);
        pressurePlate(pRecipeOutput, ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get(), Moditems.ALEXANDRITE.get());

        fenceBuilder(ModBlocks.ALEXANDRITE_FENCE.get(), Ingredient.of(Moditems.ALEXANDRITE.get())).group("alexandrite")
                .unlockedBy(getHasName(Moditems.ALEXANDRITE.get()), has(Moditems.ALEXANDRITE.get())).save(pRecipeOutput);
        fenceGateBuilder(ModBlocks.ALEXANDRITE_FENCE_GATE.get(), Ingredient.of(Moditems.ALEXANDRITE.get())).group("alexandrite")
                .unlockedBy(getHasName(Moditems.ALEXANDRITE.get()), has(Moditems.ALEXANDRITE.get())).save(pRecipeOutput);
        wall(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ALEXANDRITE_WALL.get(), Moditems.ALEXANDRITE.get());

        doorBuilder(ModBlocks.ALEXANDRITE_DOOR.get(), Ingredient.of(Moditems.ALEXANDRITE.get())).group("alexandrite")
                .unlockedBy(getHasName(Moditems.ALEXANDRITE.get()), has(Moditems.ALEXANDRITE.get())).save(pRecipeOutput);
        trapdoorBuilder(ModBlocks.ALEXANDRITE_TRAPDOOR.get(), Ingredient.of(Moditems.ALEXANDRITE.get())).group("alexandrite")
                .unlockedBy(getHasName(Moditems.ALEXANDRITE.get()), has(Moditems.ALEXANDRITE.get())).save(pRecipeOutput);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, TutorialMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
