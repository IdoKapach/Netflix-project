import { getUserService } from './User.js';

// Given a request, checks if it contains under the userId header, an exist user. otherwise, throws exeption
const authantication = async (req) => {
    // gets userId
    let userId = req.header('userId')
    try {
    // checks if userId exists. if it isn't, throws an exeption
       let id = await getUserService(userId)
       if (id == null) {
          throw "User ID wasn't found. Provide the userId header or another value for it."
       }
    }
    catch(e) {
        throw e
    }
};

export {authantication}