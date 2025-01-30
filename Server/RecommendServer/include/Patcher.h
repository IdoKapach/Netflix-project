#include "Icommand.h"
#include "IDataManager.h"
#include "Adder.h"

class Patcher : public Icommand {
    
    private:
        IDataManager* dm;
        Adder* adder;

    public:
        Patcher(IDataManager* dataManager);
        ~Patcher();
        string execute(vector<unsigned long int> arr) override;
        string getInstructions() override;
};