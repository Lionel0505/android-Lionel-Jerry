import { Body, Get, JsonController, Post, Req, UseBefore } from "routing-controllers";
import { FavoriteService } from "../services/favorite.service";
import { authentication } from "../middlewares/authentication.middleware";
import { IUserRequest } from "../utils/models/global.type";
import { formatResponse } from "../utils/utils.service";
import { Favorite } from "../entity/favorite.entity";
import { FavoriteDto } from "../utils/dto/favorite.dto";
import { validationMiddleware } from "../middlewares/validation.middleware";
import { OpenAPI } from "routing-controllers-openapi";


@JsonController('/favorites')
export class FavoriteController {

    favoriteService: FavoriteService = new FavoriteService();


    @Get()
    @UseBefore(authentication)
    @OpenAPI({summary: 'Return specified user favorites'})
    async getFavorites(@Req() request: IUserRequest) {

        try {

            const result: Favorite[] = await this.favoriteService.findFavorites(request.user);

            return formatResponse(200, 'Données trouvées', result);

        } catch (error: any) {

            return formatResponse(error.status, error.message);

        }


    }


    @Post()
    @UseBefore(authentication, validationMiddleware(FavoriteDto, 'body', false))
    @OpenAPI({summary: 'Return new favorite'})
    async addToFavorites(@Req() request: IUserRequest, @Body() favoriteData: FavoriteDto) {

        try {

            const result: Favorite = await this.favoriteService.addToFavorites(request.user, favoriteData);

            return formatResponse(200, 'Données trouvées', result);

        } catch (error: any) {

            return formatResponse(error.status, error.message);

        }

    }

}