import { Repository } from "typeorm";
import { Favorite } from "../entity/favorite.entity";
import { AppDataSource } from "../utils/db-options/db-options";
import { User } from "../entity/user.entity";
import { handleError, isEmpty } from "../utils/utils.service";
import { HttpException } from "../utils/models/global.type";
import constants from "../utils/config/constants.config.json";
import { FavoriteDto } from "../utils/dto/favorite.dto";
import { Content } from "../entity/content.entity";


export class FavoriteService {

    // Repositories
    favoriteRepository: Repository<Favorite> = AppDataSource.getRepository(Favorite);

    contentRepository: Repository<Content> = AppDataSource.getRepository(Content);


    async findFavorites(loggedInUser: User): Promise<Favorite[]> {

        if (isEmpty(loggedInUser)) throw new HttpException(510, constants.messages.error.missing_session);

        try {

            return await this.favoriteRepository
                .createQueryBuilder('favorite')
                .leftJoin('favorite.user', 'user')
                .andWhere('user.id = :userId', {userId: loggedInUser.id})
                .leftJoinAndSelect('favorite.content', 'content')
                .getMany();

        } catch (error) {

            throw handleError(error);

        }

    }


    async addToFavorites(loggedInUser: User, favoriteData: FavoriteDto): Promise<Favorite> {

        if (isEmpty(loggedInUser)) throw new HttpException(510, constants.messages.error.missing_session);

        if (isEmpty(favoriteData)) throw new HttpException(510, constants.messages.error.empty_form_data);

        try {

            const newFavorite: Favorite = new Favorite();

            const content = await this.contentRepository.findOne({
                where: { id: favoriteData.favorite_id },
                relations: ['childCategory']
            });

            if (isEmpty(content)) throw new HttpException(510, constants.messages.error.empty_entity);

            if (content!.childCategory.id != loggedInUser.childCategory.id) throw new HttpException(510, constants.messages.error.category_mismatch);

            newFavorite.content = content!;

            newFavorite.user = loggedInUser;

            return await this.favoriteRepository.save(newFavorite);

        } catch (error) {

            throw handleError(error);

        }

    }

}