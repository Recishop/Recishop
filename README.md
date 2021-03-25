# Recishop

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
A shopping list builder that helps people make shopping lists from custom recipes


### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Food/Social
- **Mobile:** Mobile first experience
- **Story:** Allows users to share recipes live with other people and help plan a trip to the grocery store
- **Market:** For individuals or groups of individuals who live together who want an easier way to save, keep track of, and reuse their favorite recipes!
- **Habit:** People can build their own profile of recipes and improve their list. Becomes viewable to others and can be shared with others.
- **Scope:** Recishop started as a very focused app that's sole purpose was to help roommates quickly and easily build shopping lists.  It can easily expand to become something that helps users track food consumption, food costs, and find new recipes through friends and other users.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Users should be able to create their own recipes and save them to a database
* Users should be able to see a list of their own created recipes
* Users should be able to view and check off items from a generated shopping list based on what recipes they have added
* Users should be able to access recipe list of other users
* Users should be able to form "Groups" with other users where they can share a shopping list and share their saved recipes with one another
* Users should be able to collaboratively add and check items off of shared shoping list 
* Maps feature to locate nearby grocery stores


**Optional Nice-to-have Stories**

* App estimates the total price of the shopping list
* The ability to add custom/random items to a generated shopping list (i.e toilet paper, deodorant, etc.)
* Users being able to view the profile pages of other users to see their saved recipes

### 2. Screen Archetypes

* Profile Screen to see user's saved recipes
    * Users should be able to create their own recipes and save them to a database
    * Users should be able to see a list of their own created recipes
* Shopping List Screen
    * Users should be able to form "Groups" with other users where they can share a shopping list and share their saved recipes with one another
    * Users should be able to collaboratively add and check items off of shared shoping list 
    * Users should be able to view and check off items from a generated shopping list based on what recipes they have added
* Recipe Crafting Screen
    * Users should be able to create their own recipes and save them to a database
    * Users should be able to see a list of their own created recipes
* Map Screen
    * Maps feature to locate nearby grocery stores

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* [Tab 1] User profile fragment
* [Tab 2] Shopping list fragment
* [Tab 3] Existing recipes fragment

**Flow Navigation** (Screen to Screen)

* User profile fragment
   * [New recipe button] Takes user to create new recipe screen
   * [Signout button] Takes user back to the login page
* Existing recipes fragment
   * [New recipe button] Takes user to the same create new recipe screen

## Wireframes
<img src="https://github.com/Recishop/Recishop/blob/master/wireframeInitial.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]

### Models
#### User
| Property      |Type           | Description                 |
| ------------- |-------------  | ------                      |
| ObjectId      | String        | The UUID for a user         |
| Username      | String        | Friendly identifier of user |
| Nickname      | String        | Nickname of user            |
| CreatedAt     | Date          | Member since field          |
| Password      | String        | User password               | 
| Email         | String        | User email                  |


#### Recipe
| Property      |Type           | Description  |
| ------------- |-------------| ----- |
| objectId | String      |    unique id for the user post (default field) |
| chef    | pointer to User | recipe creator |
| name      | String      | name of the recipe |
| createdAt | DateTime      |    date when recipe is created (default field)
| updatedAt | DateTime      |    date when post is last update (default field) |
| Ingredients | Array [Incredient] | A list of Ingredient models necessary for the recipe 

#### Ingredient
| Property     | Type         | Description | 
| --------     | ---          | ----------- |
| Name         | String       | The name of the ingredient |
| Quantity     | Double       | The quantity of ingredient necessary 
| measurement | String      |    The measurement type for a certain ingredient (i.e. cup, tsp) |

#### Group
Coming Soon...?


### Networking
#### List of network requests by screen
- Profile screen
    - (Read/GET) Query all recipes where user is chef
    - (Create) Create new shopping list
- Recipe creation/modification screen
    - (Update/PUT) Update an existing recipe
    - (Create/POST) Create a new recipe
    - (Delete) Delete existing recipes
- Shopping list screen
    - N/A
- Map screen
    - N/A
