
--User:
INSERT INTO users (userId, firstName, lastName, email, password, address) VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'password123', '123 Main Street');
INSERT INTO users (userId, firstName, lastName, email, password, address) VALUES (2, 'Jane', 'Smith', 'jane.smith@example.com', 'password456', '456 Elm Avenue');

--Item:
--Premiere Outfits:
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Solo in the Spotlight', 'Inspired by the same gown worn by Margot Robbie at the LA Premiere of Barbie', 'Full-length long-sleeve gown studded with black sequins', 'XS, S, M, L, XL', 79.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Lemon Two-piece Suit', 'This two-piece suit is inspired by the Gucci suit worn by Ryan Gosling', 'Matching lemon two-piece suit', 'XS, S, M, L, XL', 99.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Loose fitting shirt and trousers', 'A more comfortable go-to look inspired by Billie Eilish at the Barbie La Premiere', 'Pastel Pink shirt and white trousers', 'XS, S, M, L, XL', 30.50);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Grey fitted top and skirt', 'THE look for all of the barbs, inspired by the Barb herself, Nicki Minaj', 'Cool-toned grey top and skirt', 'XS, S, M, L, XL', 60.75);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Checkered two-piece', 'Inspired by Xochitl Gomex', 'Matching checkered top and skirt', 'XS, S, M, L, XL', 59.95);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Cool Blue', 'Inspired by Simu Liu', 'Matching pastel blue shirt and trousers', 'XS, S, M, L, XL', 45.95);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Black Mini Dress', 'Inspired by Shay Mitchell', 'Black mini dress with low white collar', 'XS, S, M, L, XL', 85.00);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'The Boss', 'Inspired by Greta Gerwig', 'Full-length pastel pink dress', 'XS, S, M, L, XL', 150.00);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'A Dress for All Events', 'Inspired by America Ferrera', 'Full-length sleeveless black sequin dress', 'XS, S, M, L, XL', 175.00);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Pretty in Pink', 'Inspired by Emma Mackey', 'Full-length sleeveless pink satin dress', 'XS, S, M, L, XL', 95.00);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Tye Dye but Better', 'Inspired by Dua Lipa', 'Full-length sleeveless tye dye satin dress', 'XS, S, M, L, XL', 200.00);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Hidden Glam', 'Inspired by Nicola Coughlan', 'White mini dress with lace sleeves', 'XS, S, M, L, XL', 149.99);

--Swimwear Outfits:
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Robe', 'Very Long Robe', 'Silk robe', 'XS, S, M, L, XL', 149.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Orange Jacket', 'Orange', 'Jacket', 'XS, S, M, L, XL', 15.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Babyblue Swimsuit', 'One-piece swimsuit', 'Blue swimsuit', 'XS, S, M, L, XL', 19.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Sunset', 'Pink-orange suit', 'gregergertgeget', 'XS, S, M, L, XL', 27.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Rainbow Puddle', 'Off shoulder', 'egerbgegtrg', 'XS, S, M, L, XL', 17.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'V neck', 'Plunging neckline', 'egetghetheht', 'XS, S, M, L, XL', 49.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Full Coverage', 'Brown suit', 'erghethtrhg4egre', 'XS, S, M, L, XL', 189.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Peachy', 'Cross bow piece', 'vergbergetg', 'XS, S, M, L, XL', 56.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Frilly neck', 'Bright pink suit', 'egrghewghe', 'XS, S, M, L, XL', 78.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Posh Beach', 'Posh look for the beach', 'verergeg', 'XS, S, M, L, XL', 56.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Mermaid', 'Underwater vibe', 'wgregberger', 'XS, S, M, L, XL', 76.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Florals for Spring?', 'Flowers everywhere', 'feibgeigbri9u', 'XS, S, M, L, XL', 92.99);

--Ken Outfits:
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Double Denim', 'Denim vest and jeans', 'Vest jacket and jeans', 'XS, S, M, L, XL', 39.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Pink on Pink', 'All pink outfit', 'Polo and trousers', 'XS, S, M, L, XL', 49.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Open Shirt', 'Pink and blue striped', 'Flowy shirt', 'XS, S, M, L, XL', 25.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Two Blue', 'Simple blue t-shirt', 'Cotton shirt', 'XS, S, M, L, XL', 14.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Graffiti', 'Printed shirt', 'Polyester shirt', 'XS, S, M, L, XL', 54.79);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Dad Shirt', 'Shirt for Dad', 'Collared shirt', 'XS, S, M, L, XL', 75.60);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Blue and Pink', 'Only two colours', 'Top and joggies', 'XS, S, M, L, XL', 60.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Grey Stripes', 'Grey on Grey on Grey', 'Polo Shirt', 'XS, S, M, L, XL', 23.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Coral shorts', 'Swim shorts', 'Swimwear', 'XS, S, M, L, XL', 15.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Cotton Candy', 'Stripey shirt', 'Baggy collared shirt', 'XS, S, M, L, XL', 89.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Trippy Shirt', 'Optical illusion shirt', 'Polyester cotton blend shirt', 'XS, S, M, L, XL', 41.99);
insert into Item (itemId, name, description, itemType, size, price) values (nextval('ITEM_ID_SEQ'), 'Plain Pastel', 'Plain Pastel shirt', 'Merino wool top', 'XS, S, M, L, XL', 99.99);

--Basket:
INSERT INTO basket (basketId, basketTotal, userId) VALUES (1, 0, 1);

--BasketItem:
INSERT INTO basketItem (basketItemId, basketId, itemId, quantity, size) VALUES (nextval('ITEM_ID_SEQ'), 1, 1, 2, 'S');
INSERT INTO basketItem (basketItemId, basketId, itemId, quantity, size) VALUES (nextval('ITEM_ID_SEQ'), 1, 2, 3, 'M');
INSERT INTO basketItem (basketItemId, basketId, itemId, quantity, size) VALUES (nextval('ITEM_ID_SEQ'), 1, 3, 1, 'L');