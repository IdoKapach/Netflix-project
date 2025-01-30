#include "Icommand.h"
#include "Adder.h"

class Poster : public Icommand {
    
    private:
        // the data manager to be used.
        IDataManager* dataManager;
        // nested Adder object.
        Adder* adder;

    public:
        // Constructor for the Poster class.
        // @param dataManager: the data manager to be used.
        // @param adder: the nested adder object to be used.
        // @return Poster object.
        Poster(IDataManager* dataManager);
        ~Poster();
        string execute(vector<unsigned long int> arr) override;
        string getInstructions() override;
};