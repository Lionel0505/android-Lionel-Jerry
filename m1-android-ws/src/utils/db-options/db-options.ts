import {DataSource} from "typeorm";
import path from "path";

export const AppDataSource = new DataSource({
    type: "mysql",
    host: process.env.DB_HOST,
    port: Number(process.env.DB_PORT),
    username: process.env.DB_USERNAME,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    synchronize: true,
    logging: false,
    entities: [path.join(__dirname, '../../**/*.entity{.ts,.js}')],
    migrations: [path.join(__dirname, '../../**/*.migration{.ts,.js}')],
    subscribers: [path.join(__dirname, '../../**/*.subscriber{.ts,.js}')],
});
