import { Get, JsonController, QueryParam, Req, UseBefore } from "routing-controllers";
import { authentication } from "../middlewares/authentication.middleware";
import { IUserRequest } from "../utils/models/global.type";
import { ContentService } from "../services/content.service";
import { formatResponse } from "../utils/utils.service";
import { Content } from "../entity/content.entity";
import { OpenAPI } from "routing-controllers-openapi";


@JsonController('/contents')
export class ContentController {

    contentService: ContentService = new ContentService();


    @Get()
    @UseBefore(authentication)
    @OpenAPI({summary: 'Return specific contents'})
    async getContents(@Req() request: IUserRequest, @QueryParam('content_category') contentCategoryId: number, @QueryParam('content_type') contentType: string) {

        try {

            const result: Content[] = await this.contentService.findContents(request.user, contentCategoryId, contentType);

            console.log("********", await formatResponse(200, 'Données trouvées', result));

            return await formatResponse(200, 'Données trouvées', result);

        } catch (error: any) {

            return await formatResponse(error.status, error.message);

        }

    }


    @Get('/search')
    @UseBefore(authentication)
    @OpenAPI({summary: 'Return specific contents'})
    async searchForContents(@Req() request: IUserRequest, @QueryParam('filter') filter: string) {

        try {

            const result: Content[] = await this.contentService.searchForContents(request.user, filter);

            return await formatResponse(200, 'Données trouvées', result);

        } catch (error: any) {

            return await formatResponse(error.status, error.message);

        }

    }

}