import { Controller, Get } from "routing-controllers";
import { landingPage } from "../utils/storage/html-content.storgae";
import { OpenAPI } from "routing-controllers-openapi";


const baseUrl: string = process.env.BASE_URL || '';

@Controller('/')
@OpenAPI({summary: 'Load the landing page of the API'})
export class DefaultController {

    @Get()
    async loadLandingPage() {

        return landingPage(baseUrl);

    }

}