import { BaseEntity } from "./base.entity";
import { Entity, Index, JoinColumn, ManyToOne } from "typeorm";
import { Content } from "./content.entity";
import { User } from "./user.entity";

@Entity()
@Index(['content','user'], { unique: true })
export class Favorite extends BaseEntity {

    @ManyToOne(() => Content, content => content.favorites)
    @JoinColumn({name: 'content_id'})
    content!: Content;

    @ManyToOne(() => User, child => child.favorites)
    @JoinColumn({name: 'user_id'})
    user!: User;

}