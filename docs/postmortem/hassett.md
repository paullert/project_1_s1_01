# Rian Hassett - Project 01 Retrospective
## Your links. Your merged pull requests and your issues. **
### Merged PRs
https://github.com/paullert/project_1_s1_01/pull/50
https://github.com/paullert/project_1_s1_01/pull/49
https://github.com/paullert/project_1_s1_01/pull/41
https://github.com/paullert/project_1_s1_01/pull/29
### Issues
https://github.com/paullert/project_1_s1_01/issues/36
https://github.com/paullert/project_1_s1_01/issues/35
https://github.com/paullert/project_1_s1_01/issues/20
## Your role. What did you actually build?
I built the save point feature, which required adding a DAO, Repository, Foreign keys to connect the user and the cryptocoins.
## Your biggest challenge. What was it, why was it hard, and how did you handle it?
I was struggling with connecting the two databases, I kept getting foreign key errors. The issue was that when I rebuilt the database schema the cache 
wasn't fully wiped, and it thought there was a savePoint with a coin that didn't exist yet. It took me a while to figure it out.
## The most valuable thing you learned.
This project was a great refresher of Android Studio, and learning Kotling w/ composables. I loved used the view models to make sure that that
data on screen was always up to date.
## Two commitments you carry into Project 02.
Make sure that I provide tests for all code that I push.
I'd like to work on the API side of things this project!
