import { User } from "../entity/user.entity";
import { handleError, isEmpty } from "../utils/utils.service";
import { HttpException } from "../utils/models/global.type";
import constants from "../utils/config/constants.config.json";
import { Content } from "../entity/content.entity";
import { Repository, SelectQueryBuilder } from "typeorm";
import { AppDataSource } from "../utils/db-options/db-options";


export class ContentService {

    // Repositories
    contentRepository: Repository<Content> = AppDataSource.getRepository(Content);


    async findContents(loggedInUser: User, contentCategoryId: number, contentType: string): Promise<Content[]> {

        if (isEmpty(loggedInUser)) throw new HttpException(510, constants.messages.error.missing_session);

        if (isEmpty(contentCategoryId)) throw new HttpException(510, constants.messages.error.empty_form_data);

        if (isEmpty(contentType)) throw new HttpException(510, constants.messages.error.empty_form_data);

        try {

            return await this.contentRepository
                .createQueryBuilder('content')
                .andWhere('content.type = :contentType', { contentType: contentType })
                .leftJoinAndSelect('content.contentCategory', 'contentCategory')
                .andWhere('contentCategory.id = :contentCategoryId', {contentCategoryId: contentCategoryId})
                .leftJoinAndSelect('content.childCategory', 'childCategory')
                .andWhere('childCategory.id = :childCategoryId', {childCategoryId: loggedInUser.childCategory.id})
                .getMany();

        } catch (error) {

            throw handleError(error);

        }

    }


    async searchForContents(loggedInUser: User, filter: string): Promise<Content[]> {

        if (isEmpty(loggedInUser)) throw new HttpException(510, constants.messages.error.missing_session);

        try {

            let queryBuilder: SelectQueryBuilder<Content> = this.contentRepository
                .createQueryBuilder('content')
                .leftJoinAndSelect('content.contentCategory', 'contentCategory')
                .leftJoinAndSelect('content.childCategory', 'childCategory')
                .andWhere('childCategory.id = :childCategoryId', {childCategoryId: loggedInUser.childCategory.id})

            if (!isEmpty(filter)) {

                queryBuilder = queryBuilder
                    .andWhere('content.title LIKE :contentTitle', {contentTitle: `%${filter}%`});

            }

            return queryBuilder.getMany();

        } catch (error) {

            throw handleError(error);

        }

    }

}