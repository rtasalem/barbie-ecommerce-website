# barbie-website

## Name
Barbie E-Commerce Website

## Description
This website is to sell physical barbie related items such as tops and skirts etc. The website has functionality to explore through the website pages showing particular collections and items that can be bought. There is a favourites list for saving items for a later date and a basket to add items the user wanted to buy, additionally to edit products inside the basket such as sizing, quantity and removing items.

## DoD
The clothing website is considered “done” when it is functional, responsive and meets the following criteria: all core features, including product listing, shopping basket, user registration, login and logout functionality are implemented and functioning correctly. The user interface is polished, with intuitive navigation menus, well-design pages and smooth transitions. The website is at least 80% tested, and the code is well documented.

## Badges
Tests that don't currently pass:
    Basket service          - Delete item from basket
                            - Edit size of item
                            - Add item to basket
    Favourites controller   - Delete Favourites
    Item controller         - Add item

## Roadmap
- Altering resizing of webage to make it suitable for all screen sizes
- Adding images to cart
- Adding wishlist functionality in front and backend
- Updating tests to ensure they all work
- Adding an admin side to contribute to the addition of items/maintenance 

## Contributing
To clone:
    git clone https://git.fdmgroup.com/Kuljeet.Panesar/barbie-website

To pull:
    cd barbie-website
    git branch          (ensure you are on main branch, if not use  git checkout main)
    git pull

To push:
    cd barbie-website
    git branch          (check branch that you are on)
    git checkout -b newBranch       (name newBranch as necessary)
    git branch          (check you are on the new branch)
    git status          (changes will be in red)
    git add .
    git status          (changes will appear green)
    git commit -m "description of the commit"
    git status          (check tree is clean)
    git push --set-upstream origin newBranch
Then create merge request on gitlab


Check product backlog to see any remaining tasks -> https://barbie-project.atlassian.net/jira/software/projects/SCRUM/boards/1/backlog

To open the Frontend:
    cd barbie-website
    cd frontend
    npm install
    npm start

## Authors and acknowledgment
Backend contributions: Andreea, Kuljeet, Nadeen, Rana
Frontend contributions: Laila, Maryam, Viktorija

## Project status
Partly functioning website to be added to as time goes by
