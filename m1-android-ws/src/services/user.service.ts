import constants from '../utils/config/constants.config.json';
import bcrypt from 'bcrypt';
import { UserDto } from "../utils/dto/user.dto";
import { handleError, isEmpty } from "../utils/utils.service";
import { HttpException } from "../utils/models/global.type";
import { Repository } from "typeorm";
import { User } from "../entity/user.entity";
import { AppDataSource } from "../utils/db-options/db-options";
import { AuthenticationService } from "./authentication.service";
import { ChildCategory } from "../entity/child-catgory.entity";


export class UserService {

    // Repositories
    private userRepository: Repository<User> = AppDataSource.getRepository(User);

    private childCategoryRepository: Repository<ChildCategory> = AppDataSource.getRepository(ChildCategory);

    // Services
    private authenticationService: AuthenticationService = new AuthenticationService();


    async findUserById(id: number): Promise<User> {

        if (isEmpty(id)) throw new HttpException(510, constants.messages.error.missing_field.replace('{{field}}', 'Identifiant'));

        try {

            const foundUser: User | null = await this.userRepository.findOne({
                where: {id: id},
                relations: ['childCategory']
            });

            if (isEmpty(foundUser)) throw new HttpException(510, constants.messages.error.empty_entity);

            return foundUser!;

        } catch (error) {

            throw handleError(error);

        }

    }


    async createUser(userData: UserDto): Promise<string> {

        if (isEmpty(userData)) throw new HttpException(510, constants.messages.error.empty_form_data);

        try {

            const newUser: User = new User();

            // Set the user category
            let childCategory: ChildCategory | null = null;
            const childCategories: ChildCategory[] = await this.childCategoryRepository.find();

            for (let i = 0; i < childCategories.length; i++) {

                const temp: number[] = childCategories[i].wording
                    .replace(/ans/gi, '')
                    .trim()
                    .split('-')
                    .map(element => Number(element));

                if (userData.child_category <= temp[1] &&userData.child_category >= temp[0]) {

                    childCategory = childCategories[i];
                    break;

                }

            }

            if (isEmpty(childCategory)) throw new HttpException(510, constants.messages.error.empty_entity);

            newUser.childCategory = childCategory!;

            // Random additional data
            const salt = await bcrypt.genSalt(10);

            // Replace the password with the hash
            newUser.password = await bcrypt.hash(userData.password.trim(), salt);

            // Set the username
            newUser.username = userData.username.trim();

            const createdUser: User = await this.userRepository.save(newUser);

            return this.authenticationService.signIn({
                username: createdUser.username,
                password: userData.password.trim()
            });

        } catch (error) {

            throw handleError(error);

        }

    }

}