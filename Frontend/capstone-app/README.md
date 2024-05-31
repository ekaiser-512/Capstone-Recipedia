=======================
Family Recipes Web App
=======================

Overview
--------
This is a full-stack, single-page web application designed to help users keep track of family recipes. The app allows users to create a recipe "book" and organize recipes into categories. The application is built using JavaScript, JSX, CSS, and Spring Boot for the backend.

Features
--------
User Authentication
Theme Toggle
Manage Recipe Books and Categories
Add and Manage Ingredients
Responsive Design

Technologies Used
-----------------
Frontend: React, JSX, CSS
Backend: Spring Boot, Java
API: Fetch API
Routing: React Router
Database: H2 (development), PostgreSQL (production)

========================
PROJECT STRUCTURE 
========================
~~~~~~~~
Frontend
~~~~~~~~

App.jsx
-------
Main entry point.
Key Components: Header, Footer, Routes, ThemeContext, AuthContext.

CreateRecipe.jsx
----------------
Create new recipes.
Key Components: Input, Button, useNavigate, useContext, useState, useEffect.
Functions: checkLogin, getCategories, createRecipe, addRecipeToCategory, onAddIngredientButtonClick, onSubmitButtonClick, handleIngredientChange, handleChange.

ProfileContainer.jsx
--------------------
Manage user profile.
Key Components: Profile, Button, useNavigate, useContext, useState.
Functions: handleUpdateProfile.

Login.jsx & Signup.jsx
----------------------
Handle user login and signup.
Key Components: Input, Button.
Functions: handleChange, onLoginButtonClick, onSignupButtonClick.

~~~~~~~~
Backend
~~~~~~~~

Entities
--------
Auth: id, email, password.
User: userId, firstName, lastName, dateOfBirth, email, password, book.
Book: id, title, categories.
Category: id, title, book, recipes.
Ingredient: ingredientId, name, ingredientFoodGroup, commonAllergen, dietaryRestriction, recipes.
Recipe: recipeId, name, recipeAuthor, recipeDescription, category, ingredients.

Repositories
------------
IRecipeRepository: findByName, findByCategoryId.
IAuthRepository: existsByEmail, findByEmail.
IUserRepository: findByEmail.

Configuration
--------------
RestTemplateConfig: Configures RestTemplate.
AppConfig: Configures ModelMapper.

DTOs
----------
UserRegistrationDTO: firstName, lastName, dateOfBirth, email, password.
UserLoginDTO: email, password.
CommonResponseDTO: data, message, additionalDetails, status, hasError, error.
UserUpdateDTO: id, email, firstName, lastName.
CreateBookForUserDTO: title.
CreateCategoryForBookDTO: title.


Controllers
----------
RecipeController: CRUD for recipes.
IngredientController: CRUD for ingredients.
UserController: CRUD for users.
BookController: CRUD for books.




Contact
For any questions or feedback, please contact ekaiser@indeed.com. This README provides a comprehensive overview of the Family Recipes Web App, detailing its features, technologies used, project structure, installation steps, usage instructions, and contribution guidelines.