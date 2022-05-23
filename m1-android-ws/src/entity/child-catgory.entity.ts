import { BaseEntity } from "./base.entity";
import { Column, Entity, OneToMany } from "typeorm";
import { User } from "./user.entity";
import { Content } from "./content.entity";


@Entity()
export class ChildCategory extends BaseEntity {

    @Column({
        nullable: false
    })
    wording!: string;

    @OneToMany(() => User, child => child.childCategory)
    children!: User[];

    @OneToMany(() => Content, content => content.childCategory)
    contents!: Content[];

}