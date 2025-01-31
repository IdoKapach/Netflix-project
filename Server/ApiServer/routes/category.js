import express from 'express';
var CategoryRouter = express.Router();
import {categoryController} from '../controllers/category.js';
import {authantication} from '../controllers/authantication.js';

// get and post calls for url api/categories
CategoryRouter.route('/')
    .get(authantication(), categoryController.getCategories)
    .post(authantication('admin'), categoryController.createCategory);

    // get, patch and delete calls for url api/categories/:id
CategoryRouter.route('/:id')
    .get(authantication(), categoryController.getCategory)
    .patch(authantication('admin'), categoryController.updateCategory)
    .delete(authantication('admin'), categoryController.deleteCategory);

export {CategoryRouter};