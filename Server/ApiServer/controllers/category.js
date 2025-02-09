import categoryService from '../services/category.js';

// function that responsible to create category given, name and promoted arguments. this func demands for user authantication
const createCategory = async (req, res) => {
    // tries to create the new user
    try {
        return res.json(await categoryService.createCategory(req.body.name, req.body.promoted));
    }
    // it would fail if the name or promoted weren't given or if the name is already used by another category
    catch(e) {
        if (e.length === 1) {
            return res.status(400).json({"error" : e[0]})
        }
        return res.status(400).json({"errors" : [e[0], e[1]]})
    }
};

// gets all the categories. doesn't require authantication.
const getCategories = async (req, res) => {
    res.json(await categoryService.getCategories());
};

// gets specific category. doesn't require authantication.
const getCategory = async (req, res) => {
    try {
        // gets the category object by it's Id
        const category = await categoryService.getCategoryById(req.params.id);
        res.json(category)
    }
    // sends an error in case the category Id isn't exits
    catch(e) {
        return res.status(404).json({ "error": e });
    }
};

// updates fields of exist category. require authantication.
const updateCategory = async (req, res) => {
    console.log("UODATE: controller. name: ", req.body.name, "promoted: ", req.body.promoted)
    // tries to update the category which specified by it's Id
    try {
        await categoryService.updateCategory(req.params.id, req.body.name, req.body.promoted);
        return res.status(204).json()
    }
    // throws an exeption in case no update specified or the category isn't found.
    catch(e) {
        return res.status(e[0]).json({"error" : e[1]})
    }
}

// deletes category which was specified by it's Id. require authantication.
const deleteCategory = async (req, res) => {
    // tries to delete the category
    try {
        const category = await categoryService.deleteCategory(req.params.id);
        return res.status(204).json()
    }
    // throws exeption in case the delete failed
    catch (e) {
        return res.status(404).json({ "error": e });
    }
}

export const categoryController = {createCategory, getCategories, getCategory, updateCategory, deleteCategory };