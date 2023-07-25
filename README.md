# Barbie E-commerce Website
### Authors and Acknowledgement
[Rana Salem](https://www.linkedin.com/in/ranatasalem/), [Maryam Islam](https://www.linkedin.com/in/marzcreatives/), [Laila Al-Eissa](https://www.linkedin.com/in/lailaaleissa/), [Viktorija Blumberga](https://www.linkedin.com/in/viktorijablumberga/), [Kuljeet Panesar](https://www.linkedin.com/in/kuljeetpanesar/), [Andreea-Daniela Baciu](https://www.linkedin.com/in/andreeadanielabaciu/), and Nadeen Bayley.

## Description
This website was submitted as a final group project when the authors were training towards a Tech Industry Gold Credential in software development, receiving their training through FDM Group. The project is a Barbie themed e-commerce website that sells Barbie themed clothing. The website has functionality to explore through the website pages showing particular collections of outfits and items. Refer to the initial commit made to the main branch to gain an idea of what the authors achieved in 4 days before submitting the project. This repository now serves for the authors to continue working on what was built in those 4 days. 

## Getting Started
After cloning the repository, follow the steps below to see the website on your local machines:
In your chosen IDE (the authors used Eclipse):
- Import the folder named backend as a Maven project
- Run the project as a Spring Boot App
Once you are satisfied that the API is up and running (ensure you can see tables dropping in the console), open your terminal and run the following commands to view the front-end:
- cd barbie-website/frontend
- npm install
- npm start

## Contributing & Using Git
To clone:
- git clone https://github.com/rtasalem/barbie-website.git
    
To pull:
- cd barbie-website
- git branch (ensure you are on main branch, if not use: git checkout main)
- git pull
    
To push:
- cd barbie-website
- git branch (check which branch you are on)
- git checkout -b new-branch-name (replace new-branch-name with an appropriate name)
- git branch (check you are on the new branch)
- git status (unadded files will appear red)
- git add .
- git status (added files will appear green)
- git commit -m "description of the commit"
- git status (check tree is clean)
- git push --set-upstream origin new-branch-name
    
Then create a merge/pull request here on GitHub.
## Project status
WIP (work in progress): not all desired functionality has been achieved, this will be improved upon over time.

## Roadmap
- Altering resizing of webage to make it suitable for all screen sizes
- Adding images to cart
- Adding wishlist functionality in front- and back-end
- Updating tests to ensure they all work
- Adding an admin side to contribute to the addition of new items/maintenance of an inventory
- Ensuring all tests pass for all controller and service classes (code coverage is currently 93.8%)

## Team Organisation
Whilst working on this project, the authors used Scrum and Agile principles to organise and prioritise their tasks. This included daily scrums, a scrum retrospective, working in sprints, and composing a product backlog. Kanban boards and product backlogs were managed using Jira Software. 
### DoD
The e-commerce website is considered “done” when it is functional, responsive, and meets the following criteria: all core features, including product listing, shopping basket, user registration, login and logout functionality are implemented and functioning correctly. The user interface is polished, with intuitive navigation menus, well-designed pages and smooth transitions. The website is at least 80% tested, and the code is well documented.
