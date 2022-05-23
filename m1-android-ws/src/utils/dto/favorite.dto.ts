import { IsNotEmpty, IsNumber } from "class-validator";


export class FavoriteDto {

    @IsNumber()
    @IsNotEmpty()
    favorite_id!: number;

}