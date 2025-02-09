import Category from '../models/category.js';
import movieServices from './movie.js'

// function that responsible to create category given, name and promoted arguments.
const createCategory = async (name, promoted) => {
    let errors = []
    // checks if name was given
    if (name === undefined) {
        errors.push("Name is required")
    }
    // checks if promoted was given
    if (promoted === undefined || ((promoted != true) && (promoted != false))) {
        errors.push("Promoted is required")
    }
    // checks if at least one of fields wasn't given
    if (errors.length) {
        throw errors
    }
    // in case error didn't occur, creating the category
    let category
    try{
        category = await new Category({ name:name, promoted:promoted, movies:[] });
        return await category.save();
    }
    // in case category's name is already in use
    catch (e){
        throw ["The category name is already used by existing category"]
    }
};

// gets specific category by it's Id.
const getCategoryById = async (id) => {
    // searches for the category
    try {
        const category = await Category.findById(id);
        if (!category) {
            throw "error"
        }
        // returns it if it was found
        return category
    }
    // throws exeption in case it wasn't found
    catch {
        throw "Category wasn't found";
    }
};

// gets all categories
const getCategories = async () => {
    return await Category.find({});
};

// updates fields of exist category. 
const updateCategory = async (id, name=null, promoted=null) => {
    let category = null
    console.log("UODATE: services. name: ", name, "promoted: ", promoted)
    // searches for the category. returns an error in case it isn't exist
    try {
        category = await getCategoryById(id);
    }
    catch (e) {
        throw [404, e]
    }
    // if no field was specified, returning error
    if (name == undefined && promoted == undefined) {
        throw [400, "No field was specified"]
    }
    // if promoted was provided, with bool val, updates it.
    if (promoted != undefined) {
        // returning error in case the value that was given isn't bool
        if (promoted != true && promoted != false) {
            throw [400, "Invalid value for promoted"]
        }
        // carrying out the placement
        category.promoted = promoted;
    }
    // if name was provided
    if (name != undefined) { 
        // Carrying out the placement
        category.name = name;
    }
    // saving changes
    await category.save();
    return category;
};

// deletes category which was specified by it's Id.
const deleteCategory = async (id) => {
    let category = null;
    // search after the category and return an error in case it wasn't found
    try {
        category = await getCategoryById(id);
    }
    catch (e) {
        throw e
    }
    // remove the category from any category list in any movie that under it
    for (var movieId of category.movies) {
        var movie = await movieServices.getMovieById(movieId)
        movie.categories = movie.categories.filter(cat => cat !== category.name)
        movie = await movie.save()
        if (movie.categories.length === 0) {
            await movieServices.deleteMovie(movieId)
            continue
        }
    }
    // delete the category
    await category.deleteOne();
    return category;
};

export default {createCategory, getCategoryById, getCategories, updateCategory, deleteCategory }