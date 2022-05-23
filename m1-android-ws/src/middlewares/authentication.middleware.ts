import 'dotenv/config';
import { TokenExpiredError } from 'jsonwebtoken';
import { NextFunction, Response } from "express";
import { UserService } from "../services/user.service";
import { isEmpty, retrieveTokenData } from "../utils/utils.service";
import { User } from "../entity/user.entity";
import { HttpException, ITokenData, IUserRequest } from "../utils/models/global.type";


export const authentication = async (req: IUserRequest, res: Response, next: NextFunction) => {

    try {

        const verificationResponse: ITokenData = await retrieveTokenData(req);
        const userService: UserService = new UserService();
        const userId: number = verificationResponse.user_id!;
        const foundUser: User | null = await userService.findUserById(userId!);

        if (!isEmpty(foundUser)) {

            req.user = foundUser;
            next();

        } else {

            next(new HttpException(401,'Session not found'));

        }

    } catch (error) {

        if (error instanceof TokenExpiredError) {

            next(new HttpException(402, 'Session expired'));

        } else {

            console.error(error);
            next(new HttpException(401, 'Session not found'));

        }


    }

};
