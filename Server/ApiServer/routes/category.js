import express from 'express';
var CategoryRouter = express.Router();
import {categoryController} from '../controllers/category.js';

// get and post calls for url api/categories
CategoryRouter.route('/')
.get(categoryController.getCategories)
.post(categoryController.createCategory);
// get, patch and delete calls for url api/categories/:id
CategoryRouter.route('/:id')
.get(categoryController.getCategory)
.patch(categoryController.updateCategory)
.delete(categoryController.deleteCategory);

export {CategoryRouter};