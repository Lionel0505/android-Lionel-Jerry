import { BaseEntity } from "./base.entity";
import { Column, Entity, JoinColumn, ManyToOne, OneToMany } from "typeorm";
import { ChildCategory } from "./child-catgory.entity";
import { Favorite } from "./favorite.entity";

@Entity()
export class User extends BaseEntity {

    @Column({
        nullable: false
    })
    username!: string;

    @Column({
        nullable: false
    })
    password!: string;

    @ManyToOne(() => ChildCategory, childCategory => childCategory.children)
    @JoinColumn({name: 'child_category_id'})
    childCategory!: ChildCategory;

    @OneToMany(() => Favorite, favorite => favorite.user)
    favorites!: Favorite[];

}