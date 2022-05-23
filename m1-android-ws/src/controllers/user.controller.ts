import { Body, JsonController, Post, UseBefore } from "routing-controllers";
import { validationMiddleware } from "../middlewares/validation.middleware";
import { UserDto } from "../utils/dto/user.dto";
import { formatResponse } from "../utils/utils.service";
import { UserService } from "../services/user.service";
import { OpenAPI } from "routing-controllers-openapi";


@JsonController('/users')
export class UserController {

    private userService: UserService = new UserService();


    @Post('/sign_up')
    @UseBefore(validationMiddleware(UserDto, 'body', false))
    @OpenAPI({summary: 'Return token after user creation'})
    async signUp(@Body() userData: UserDto) {

        try {

            const result: string = await this.userService.createUser(userData);

            return await formatResponse(200, 'Inscription réussie', result);

        } catch (error: any) {

            return await formatResponse(error.status, error.message);

        }

    }


}