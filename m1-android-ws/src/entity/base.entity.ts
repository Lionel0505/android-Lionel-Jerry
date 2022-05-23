import { Column, PrimaryGeneratedColumn } from "typeorm";


export abstract class BaseEntity {

    @PrimaryGeneratedColumn()
    id!: number;

    @Column({
        type: 'timestamp',
        default: () => 'CURRENT_TIMESTAMP',
        nullable: false
    })
    createdAt!: Date;

    @Column({
        type: 'timestamp',
        nullable: true
    })
    updatedAt?: Date;

}