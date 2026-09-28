# Project 01 Post Mortem - project_1_s1_01


## Context
We set out to build a cryptocurrency application that displayed useful information for the selected cryptocurrencies by the user. We set out to build an app where the user could track the changes in the cryptomarket of their favorite coins at any point in time.
We were able to implement the main goal along with a save point feature to show the old price of when the user saved it, and the current price. While the save point wasn’t as detailed as we’d like it to be, it does display the date from which the user clicked “save point” and the changes since then.

## By the numbers
- Issues opened: 33 [Issues](https://github.com/paullert/project_1_s1_01/issues) | closed: 22
- Pull requests opened: 25 [PRs](https://github.com/paullert/project_1_s1_01/pulls) | merged: 25
- Planned at kickoff: [17] stories | done: 12

## What went well
1. The API and Database accessing - Most of the database functions worked as expected after initial declaration. The API calls retrieved information cleanly, and the DAO objects successfully interacted with the database

## What went wrong
1. Database Foreign Key Constraints - Cause: Despite being linked as a foreign key, using referencial id’s to link save points and users caused many errors. It required an extra step that was unknown for a large part of the project.
2. View Model Factories - Cause: The view models implemented required factory objects to properly tie function actions and data to each view model which caused many errors during development.

## Advice to our next teams
1. Be sure to push PR’s and code work earlier than later. Late PRs are hard to deal with
2. Be sure to spread work evenly, don’t let teammates take work that you would like to do.
3. Make sure to understand what the project requires to ensure you can create github issues that make sense and aren’t vague.
