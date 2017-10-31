#include <iostream.h>
template <class T> class pair {
			T value1, value2;
public:
	pair (T first, T second) {
        value1=first;
        value2=second;
    }
    T getmax ();
};

template <class T>
T pair::getmax (){
    T retval;
    retval = value1>value2? value1 : value2;
    return retval;
}

int main () {
    pair  myobject (100, 75);
    cout << myobject.getmax();
    return 0;
}