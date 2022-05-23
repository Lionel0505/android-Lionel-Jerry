-- Child category data
INSERT INTO `child_category` (`id`, `createdAt`, `updatedAt`, `wording`)
VALUES (1, '2022-05-22 10:31:27', NULL, '3-5 ans'),
       (2, '2022-05-22 10:31:27', NULL, '6-8 ans'),
       (3, '2022-05-22 10:31:44', NULL, '9-11 ans');

-- Content category data
INSERT INTO `content_category` (`id`, `createdAt`, `updatedAt`, `wording`)
VALUES (1, '2022-05-22 11:36:24', NULL, 'Santé'),
       (2, '2022-05-22 11:36:24', NULL, 'Apprentissage'),
       (3, '2022-05-22 11:36:36', NULL, 'Divertissement');

-- Content data
INSERT INTO `content` (`id`, `createdAt`, `updatedAt`, `title`, `description`, `path`, `contentType`,
                       `child_category_id`, `content_category_id`)
VALUES (1, '2022-05-22 12:35:40', NULL, 'Se brosser les dents',
        '    Un brossage régulier des dents est nécessaire pour que ses dents restent belles et en bonne santé, pour éviter l’apparition de caries, et aussi pour toujours avoir une haleine fraîche.\r\n\r\n    Un brossage des dents régulier est indispensable pour la bonne santé des dents.\r\n					 Pour avoir une bonne hygiene il faut respecter la règle des 3-3-3:\r\n    -se brosser les dents 3fois par jour\r\n    -pendant au moins 3mn\r\n    -et bien brosser les 3 cotes',
        '', 'TXT', 1, 1),
       (2, '2022-05-22 12:37:44', NULL, 'Se laver les mains',
        '    Le lavage des mains est un geste primordial dans la vie de tous les jours, qui permet de réduire le risque de maladies ou d’éviter la transmission de ces microbes à d’autres personnes.\r\n\r\nComment se laver les mains?\r\n    Pour se laver les mains, il faut évidemment se servir de savon, soit solide, soit liquide : l’eau seule ne lave pas!\r\n    -Se mouiller les mains\r\n    -Prendre du savon\r\n    -Mousser, frotter les mains, dessus, dessous et entre les doigts, pendant, si possible, au moins 30 secondes \r\n    -Rincer les mains sous l’eau claire\r\n    -Sécher les mains sur une serviette',
        '', 'TXT', 1, 1),
       (3, '2022-05-22 12:41:55', NULL, 'Une bonne alimentation',
        'Pour être grand et fort, il faut avoir une bonne hygiene  de vie et cela commence par la nutrition.\r\n\r\nQu’est ce qu’on doit manger?\r\n    -des aliments riches en nutriments par exemple des fruits et des legumes crus					 \r\n    -de la viande et du poisson					 \r\n\r\nComment manger?					 \r\n    -il faut éviter les aliments trop sales et/ou trop sucres\r\n    -il ne faut pas abuser des aliments peu nutritifs comme les croquettes de poulet et les desserts chimiques\r\n    -manger devant un écran est mal sain que ce soit au niveau social ou au niveau nutritionnel',
        '', 'TXT', 2, 1),
       (4, '2022-05-22 12:41:55', NULL, 'Se moucher',
        '    Quand on a le nez bouché ou que son nez coule, il faut se moucher pour dégager son nez.\r\n\r\n    Il est important d’apprendre à se moucher narine par narine en soufflant d’abord d’un côté, puis de l’autre (l’ordre n’a pas d’importance), et bien sûr de jeter ensuite son mouchoir à la poubelle.',
        '', 'TXT', 2, 1),
       (5, '2022-05-22 12:43:42', NULL, 'Prendre un bain tout seul',
        '    Le bain (ou la douche) est une étape essentielle à l’hygiène.\r\n\r\n    Il se prend en général une fois par jour, sans horaire obligatoire.\r\n\r\n    Pour ce faire, il faut d’abord se mouiller avec de l’eau.\r\n\r\n    Lorsque le corps est bien humide, on prend le savon ou le gel douche pour mousser et frotter le corps. Il faut insister sur les parties du corps qui se suent facilement comme le cou et les aisselles.\r\n\r\n    Bien rincer tout le corps avec de l’eau.\r\n\r\n    Et en fin, s’essuyer avec une serviette propre.',
        '', 'TXT', 3, 1),
       (6, '2022-05-22 12:44:46', NULL, 'Laver ses sous-vêtements',
        '    Pour être bien dans sa peau, il faut avoir des sous vêtements bien propres.\r\n\r\n    Voici 5 étapes a suivre pour laver ses sous vêtements\r\n\r\n    -Prenez une grande bassine ou un seau d’eau, remplissez là environ de 4 à 6 litres d’eau.\r\n\r\n    -Vous pouvez utiliser une lessive douce ou un savon de Marseille en guise de produits lessive. Ils sont parfaits pour vos sous-vêtements délicats ainsi que vos vêtements de sport\r\n\r\n    -Mélangez bien vos vêtements et laissez-les tremper à une température de 30°C. Attention : n’excédez pas les 10 minutes. Tout en frottant doucement\r\n\r\n    -Rincez vos vêtements. De préférence à l’eau tiède et claire. Il ne faut plus que vos vêtements soient mousseux\r\n\r\n    -Étendez vos vêtements délicats à plat. Il faut éviter de les pendre pour les faire sécher.',
        '', 'TXT', 3, 1);

