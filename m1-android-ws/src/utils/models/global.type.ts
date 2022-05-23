import { User } from "../../entity/user.entity";
import { Request } from "express";
import { HttpError } from "routing-controllers";


export class HttpException extends HttpError {

    public status: number;

    public message: string;


    constructor(status: number, message: string) {

        super(status, message);
        this.status = status;
        this.message = message;

    }

}


export interface IUserRequest extends Request {

    user: User;

}


export interface IResponseType {

    status: boolean | number;

    message: string;

    data?: any;

}


export interface ITokenData {

    jti?: string;

    user_id?: number;

    iat?: any;

    exp?: any;

    email?: any;

}
