import { Column, Entity, OneToMany } from "typeorm";
import { BaseEntity } from "./base.entity";
import { User } from "./user.entity";
import { Content } from "./content.entity";

@Entity()
export class ContentCategory extends BaseEntity {

    @Column({
        nullable: false
    })
    wording!: string;

    @OneToMany(() => Content, content => content.contentCategory)
    contents!: Content[];

}