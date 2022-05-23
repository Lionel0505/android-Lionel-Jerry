import 'dotenv/config';
import 'reflect-metadata';
import express from 'express';
import morgan from 'morgan';
import cors from 'cors';
import { getMetadataArgsStorage, useExpressServer } from "routing-controllers";
import { routingControllersToSpec } from "routing-controllers-openapi";
import { validationMetadatasToSchemas } from "class-validator-jsonschema";
import { AppDataSource } from "./utils/db-options/db-options";
import { DefaultController } from "./controllers/default.controller";
import { AuthenticationController } from "./controllers/authentication.controller";
import { ContentController } from "./controllers/content.controller";
import { FavoriteController } from "./controllers/favorite.controller";
import { UserController } from "./controllers/user.controller";


const swaggerUI = require("swagger-ui-express");


class Server {

    private readonly app: express.Application;

    private readonly port: string | number;

    private readonly databaseUri: string;


    constructor(controllers: Function[]) {

        // Setting express app
        this.app = express();

        // Setting server port form the env vars or if null use 3200
        this.port = process.env.PORT || 3200;

        // Setting the database connection url
        this.databaseUri = process.env.DATABASE_URI || 'mongodb://localhost:27107/ekaly_db';

        // Setting the middlewares
        this.setMiddlewares();

        // Setting the routes
        this.setRoutes(controllers);

        // Setting the API documentation
        this.setApiDocs(controllers);

    }


    public setRoutes(controllers: Function[]): void {

        useExpressServer(this.app, {
            cors: true,
            controllers: controllers, // we specify controllers we want to use
            defaultErrorHandler: false
        });

    }


    public async run(): Promise<void> {

        this.app.listen(this.port, async () => {

            try {

                console.log('-------------------- DB CONNECTION - ATTEMPT --------------------');

                await AppDataSource.initialize();

                console.log('-------------------- DB CONNECTION - SUCCEED --------------------');

            } catch (error) {

                console.log(`-------------------- DB CONNECTION - FAILED --------------------\n${ error }\n-------------------- DB CONNECTION - FAILED --------------------`);

            }

        });

    }


    private setMiddlewares(): void {

        // Use morgan for http log
        this.app.use(morgan('dev'));
        this.app.use(express.json({limit: 1024 * 1024 * 10, type: 'application/json'}));
        this.app.use(express.urlencoded({extended: false, limit: 1024 * 1024 * 10}));
        this.app.use(cors({origin: '*'}));

    }


    private setApiDocs(controllers: Function[]): void {

        const {defaultMetadataStorage} = require('class-transformer/cjs/storage');

        const schemas = validationMetadatasToSchemas({
            classTransformerMetadataStorage: defaultMetadataStorage,
            refPointerPrefix: '#/components/schemas/',
        });

        const routingControllersOptions = {
            controllers: controllers,
        };

        const storage = getMetadataArgsStorage();
        const specs = routingControllersToSpec(storage, routingControllersOptions, {
            components: {
                schemas,
                securitySchemes: {
                    basicAuth: {
                        scheme: 'basic',
                        type: 'http',
                    },
                },
            },
            info: {
                description: 'Generated with `routing-controllers-openapi`',
                title: 'Sample API documentation for M1 android project',
                version: '1.0.0',
            },
        });


        this.app.use('/docs', swaggerUI.serve, swaggerUI.setup(specs));

    }

}


// Setting up the server with the controllers
const server: Server = new Server([
    AuthenticationController,
    ContentController,
    DefaultController,
    FavoriteController,
    UserController
]);


// Running the server
server.run().then(() => {

    console.log(`
██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗
╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝

 █████╗ ██████╗ ██████╗     ██╗███████╗    ██████╗ ██╗   ██╗███╗   ██╗███╗   ██╗██╗███╗   ██╗ ██████╗ ██╗
██╔══██╗██╔══██╗██╔══██╗    ██║██╔════╝    ██╔══██╗██║   ██║████╗  ██║████╗  ██║██║████╗  ██║██╔════╝ ██║
███████║██████╔╝██████╔╝    ██║███████╗    ██████╔╝██║   ██║██╔██╗ ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗██║
██╔══██║██╔═══╝ ██╔═══╝     ██║╚════██║    ██╔══██╗██║   ██║██║╚██╗██║██║╚██╗██║██║██║╚██╗██║██║   ██║╚═╝
██║  ██║██║     ██║         ██║███████║    ██║  ██║╚██████╔╝██║ ╚████║██║ ╚████║██║██║ ╚████║╚██████╔╝██╗
╚═╝  ╚═╝╚═╝     ╚═╝         ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝

██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗██╗
╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝╚═╝
    `);

});