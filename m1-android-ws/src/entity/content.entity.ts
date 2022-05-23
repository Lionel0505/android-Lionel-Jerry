import { BaseEntity } from "./base.entity";
import { Column, Entity, JoinColumn, ManyToOne, OneToMany } from "typeorm";
import { E_ContentType } from "../utils/enums/static.enums";
import { ChildCategory } from "./child-catgory.entity";
import { ContentCategory } from "./content-category.entity";
import { Favorite } from "./favorite.entity";


@Entity()
export class Content extends BaseEntity {

    @Column({
        nullable: false
    })
    title!: string;

    @Column({
        type: 'text',
        nullable: true
    })
    description?: string;

    @Column({
        nullable: false
    })
    path!: string;

    @Column({
        type: 'enum',
        enum: E_ContentType,
        default: E_ContentType.TXT,
    })
    contentType!: E_ContentType;

    @ManyToOne(() => ChildCategory, childCategory => childCategory.contents)
    @JoinColumn({name: 'child_category_id'})
    childCategory!: ChildCategory;

    @ManyToOne(() => ContentCategory, contentCategory => contentCategory.contents)
    @JoinColumn({name: 'content_category_id'})
    contentCategory!: ContentCategory;

    @OneToMany(() => Favorite, favorite => favorite.content)
    favorites!: Favorite[];

}