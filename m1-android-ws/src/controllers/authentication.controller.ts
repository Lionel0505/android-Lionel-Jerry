import { Body, JsonController, Post, UseBefore } from "routing-controllers";
import { validationMiddleware } from "../middlewares/validation.middleware";
import { SignInDto } from "../utils/dto/user.dto";
import { AuthenticationService } from "../services/authentication.service";
import { formatResponse } from "../utils/utils.service";
import { OpenAPI } from "routing-controllers-openapi";


@JsonController('/auth')
export class AuthenticationController {

    private authenticationService: AuthenticationService = new AuthenticationService();


    @Post('/sign_in')
    @UseBefore(validationMiddleware(SignInDto, 'body', false))
    @OpenAPI({summary: 'Return token after signing in'})
    async signIn(@Body() userData: SignInDto) {

        try {

            const result: string = await this.authenticationService.signIn(userData);

            return formatResponse(200, 'Connexion réussie', result);

        } catch (error: any) {

            return formatResponse(error.status, error.message);

        }

    }

}