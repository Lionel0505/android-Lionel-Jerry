import constants from "../utils/config/constants.config.json";
import bcrypt from 'bcrypt';
import { Repository } from "typeorm";
import { User } from "../entity/user.entity";
import { AppDataSource } from "../utils/db-options/db-options";
import { SignInDto } from "../utils/dto/user.dto";
import { generateToken, handleError, isEmpty } from "../utils/utils.service";
import { HttpException } from "../utils/models/global.type";


export class AuthenticationService {

    private userRepository: Repository<User> = AppDataSource.getRepository(User);


    async signIn(userData: SignInDto): Promise<string> {

        if (isEmpty(userData)) throw new HttpException(510, constants.messages.error.empty_form_data);

        try {

            const currentUser: User | null = await this.userRepository.findOne({
                where: {username: userData.username.trim()}
            });

            if (isEmpty(currentUser)) throw new HttpException(510, constants.messages.error.empty_entity);

            if (!await bcrypt.compare(userData.password.trim(), currentUser!.password!)) throw new HttpException(510, constants.messages.error.password_issue);

            return generateToken({user_id: currentUser!.id}, process.env.EXPIRES_IN || '30d');

        } catch (error) {
            
            throw handleError(error);

        }

    }


}