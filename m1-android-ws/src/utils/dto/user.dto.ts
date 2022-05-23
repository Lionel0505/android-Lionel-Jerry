import { IsNotEmpty, IsNumber, IsString, MaxLength, MinLength } from "class-validator";


export class SignInDto {

    @IsString()
    @IsNotEmpty()
    @MinLength(3, {
        message: 'Nom d\' utilisateur trop court.'
    })
    @MaxLength(20, {
        message: 'Nom d\' utilisateur trop long.'
    })
    username!: string;

    @IsString()
    @IsNotEmpty()
    @MinLength(6, {
        message: 'Mot de passe trop court.'
    })
    password!: string;

}


export class UserDto extends SignInDto {

    @IsNumber()
    @IsNotEmpty()
    child_category!: number;

}