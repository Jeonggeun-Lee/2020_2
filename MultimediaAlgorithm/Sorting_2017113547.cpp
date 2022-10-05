#include <iostream>
#include <vector>
#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <chrono>
#include <random>
#include <ctime>
#include <cstdlib>

#define IS_FULL(ptr) (!(ptr))
#define FALSE 0
#define TRUE 1

#define BILLION 1000000000.0F
#define INITIAL_DATA_NUM 1000

using namespace std;
using namespace chrono;
class Student{
public:
	string id;
	string name;
	string major;
	friend ostream& operator<<(ostream& os, const Student& student);

};

ostream& operator<<(ostream& os, const Student& student){
	os << student.id << "/" << student.name << "/" << student.major << endl;
	return os;
}
ostream& endl(ostream& os){
	os << '\n';
	fflush(stdout);
	return os;
}
int CompareID(Student a, Student b){
	if(a.id > b.id) return 1;
	else if(a.id == b.id) return 0;
	else return -1;
}
int CompareMajor(Student a, Student b){
	if(a.major > b.major) return 1;
	else if(a.major == b.major) return 0;
	else return -1;
}
int CompareName(Student a, Student b){
	if(a.name > b.name) return 1;
	else if(a.name == b.name) return 0;
	else return -1;
}
template <class T>
void SwapValue(T& i, T& j){
	T t;
	t = i;
	i = j;
	j = t;
}

template <class T>
void Print(vector< T > data){
	cout << endl;
	cout << "=================" << endl;
	cout << "Students List" << endl;
	cout << "=================" << endl;
	for(int i=0; i<data.size(); i++){
		cout << data[i];
	}
	cout << endl;
}
/*
template <class T>
void DataStep(vector< T > quickData, int& step){
	step++;
	cout << endl << step << " loops step: " << endl;
	QuickPrint(quickData);
cout << endl;
}
*/

template <class T>
void Split(vector< T >& quickData, int m, int* up, int (* Compare)(T,T) ){
	T v = quickData[m];
	int low = m+1;
	int M = (*up);
	bool done = false;
	while( !done ){
		while( low <= *up && Compare(quickData[low], v) <= 0 ){
			low++;
		}//CORRECTED
		while( ( low <= *up && Compare(v, quickData[*up]) <= 0 ) ){
			(*up)--;
		}//CORRECTED
		if( (*up) < low ){
			done = true;
		}
		else{
			SwapValue< T >(quickData[low], quickData[*up]);
		}//CORRECTED
	}
	quickData[m] = quickData[*up];
	quickData[*up] = v;
}

template <class T>
void QuickSort(vector< T >& quickData, int p, int q, int (* Compare)(T,T)){
	int j = q;
	if(p<q){
		Split< T >(quickData, p, &j, Compare);
		//DataStep(quickData, step);
		QuickSort< T >(quickData, p, j-1, Compare);
		QuickSort< T >(quickData, j+1, q, Compare);
	}
}

template <class T>
void HeapSort(vector<T>& data, int(* Compare)(T, T)){
	int n = data.size();
	data.insert(data.begin(),data.front()); 
	int i, j, k, step;
	T x;
	step = 1;
	for(k = n/2; k>0; k--){
		i = k;
		x = data[i];
		while( (j=2*i) <= n) {
			if(j<n && Compare(data[j],data[j+1])<0) j++;
			if(Compare(x,data[j])>=0) break;
			data[i] = data[j];
			i = j;
		}
		data[i] = x;
	}
	while(n>1){
		x = data[n];
		data[n] = data[1];
		n--;
		i=1;
		while( (j=2*i)<=n ){
			if(j<n && Compare(data[j],data[j+1])<0) j++;
			if(Compare(x,data[j])>=0) break;
			data[i] = data[j];
			i = j;
		}
		data[i] = x;
		//cout << endl << step << "loops step:";
		//HeapPrint(data);
		step++;
	}
	data.erase(data.begin()+0);
	vector<Student>(data).swap(data);
}

/*
template<class T>
void HeapPrint(vector<T>& data){
	int i;
	for(i=1; i< data.size(); i++){
		cout << data[i];
	}
	cout << endl;
}
*/


int Menu(){
	cout << "================" << endl;
	cout << "Student Database" << endl;
	cout << "================" << endl;
	cout << "Choice a function" << endl;
	cout << "1. Insert 2. Sort 3. Delete 4. Print 5.Quit" << endl;
	int choice;
	while(true){
		cout << "<<"; cin >> choice;
		if(choice >=1 && choice <=5){
			break;
		}
		cout << "Invalid input. Choice again" << endl;
		if(cin.fail()){
			cin.clear();
			cin.ignore(1000,'\n');
		}
	}
	return choice;
}

void Insert( vector<Student>& data){
	Student element;
	cout << "Enter ID:"; cin >> element.id;
	cin.ignore(32767, '\n');
	cout << "Enter name:"; getline(cin,element.name);
	cout << "Enter major:"; cin >> element.major;
	cin.ignore(32767, '\n');
	int i;
	high_resolution_clock::time_point start = high_resolution_clock::now();
	data.push_back(element);
	high_resolution_clock::time_point end = high_resolution_clock::now();
	auto duration = duration_cast<nanoseconds>(end-start).count();
	cout << "Inserting Completed" << endl;
	cout << "Execution time length(seconds):" << duration/BILLION << endl;
}

void Sort(vector<Student>& data){
	int (* Compare)(Student, Student); 
	cout << "Select criterion" << endl;
	cout << "1.ID 2. Name 3. Major" << endl;
	int sel;
	while(true){
		cout << "<<"; cin >> sel;
		if(sel >=1 && sel <=3){
			break;
		}
		cout << "Invalid input. Select again" << endl;
		if(cin.fail()){
			cin.clear();
			cin.ignore(1000,'\n');
		}
	}
	switch(sel){
		case 1:
			Compare = CompareID;
			break;
		case 2:
			Compare = CompareName;
			break;
		case 3:
			Compare = CompareMajor;
			break;
	}
	cout << "Select Algorithm" << endl;
	cout << "1.Quick sort 2. Heap sort" << endl;
	while(true){
		cout << "<<" ; cin >> sel;
		if(sel >=1 && sel <=2){
			break;
		}
		cout << "Invalid input. Select again" << endl;
		if(cin.fail()){
			cin.clear();
			cin.ignore(1000,'\n');
		}
	}
	high_resolution_clock::time_point start = high_resolution_clock::now();
	switch(sel){
		case 1:
			QuickSort<Student>(data, 0, data.size()-1, Compare);
			break;
		case 2:
			HeapSort<Student>(data, Compare);
			break;
	}
	high_resolution_clock::time_point end = high_resolution_clock::now();
	auto duration = duration_cast<nanoseconds>(end-start).count();
	cout << "Sorting Completed" << endl;
	cout << "Execution time length(seconds):" << duration/BILLION << endl;
}

void Delete(vector<Student>& data){
	string id;
	cout << "Enter Student ID:";
	cin >> id;
	int i;
	high_resolution_clock::time_point start = high_resolution_clock::now();
	for(i=0; data[i].id != id && i<data.size(); i++);
	if(i == data.size()){
		cout << "ID does not exist" << endl;
	}
	else{
		data.erase(data.begin()+i);
	}
	vector<Student>(data).swap(data);
	high_resolution_clock::time_point end = high_resolution_clock::now();
	auto duration = duration_cast<nanoseconds>(end-start).count();
	cout << "Deleting Completed" << endl;
	cout << "Execution time length(seconds):" << duration/BILLION << endl;
}
void Print(vector<Student> data){
	high_resolution_clock::time_point start = high_resolution_clock::now();
	Print<Student>(data);
	high_resolution_clock::time_point end = high_resolution_clock::now();
	auto duration = duration_cast<nanoseconds>(end-start).count();
	cout << "Printing Completed" << endl;
	cout << "Execution time length(seconds):" << duration/BILLION << endl;
}



void GenerateData(vector<Student>& data){
	vector<string> majorList{
		"�ұ��к�", "������к�", "�Ϻ��а�", "�߾��߹��а�", "ö�а�", "���а�", "�Ź�����а�", "���а�", "ȭ�а�", "����а�", "�����ݵ�ü���к�",
		"���а�", "��ġ�����к�", "�����а�", "��������а�", "��ȸ��������к�", "��ǰ��������а�", "����ȫ���а�", "��ȸ�����а�", "���������к�",
		"�濵�а�", "ȸ���а�", "�濵�����а�", "���̿�ȯ����а�", "������а�", "��ǰ������а�", "�ǻ�����а�",
		"�Ǽ�ȯ����а�", "�����к�", "���κ����������а�", "��Ƽ�̵����а�", "����ý��۰��а�", "���տ������ż�����а�", "����������к�",
		"��ǻ��������Ű��к�", "��ǻ�Ͱ��а�", "������Ű�������", "ȭ���������а�",
		"�����а�", "�������", "���米����", "����������", "���б�����", "����������", "ü��������",
		"�ұ��̼�����", "�ѱ�ȭ����", "����ȭ����", "��������", "�����к�", "���а�", "���պ����а�", "��ȸ��������а�", "�۷ι������а�"};
	int i,j;
	long id;
	string name, major;
	char ch;
	random_device rd;
	mt19937 gen(rd());
	uniform_int_distribution<int> dis(0, 9999999);
	srand(time(NULL));
	for(i=0; i<INITIAL_DATA_NUM; i++){
		id = dis(gen)+2010000000;
		name = "";
		ch = rand()%('Z'-'A')+'A';
		name += ch;
		for(j = 0; j<rand()%10; j++){
			ch = rand()%('z'-'a')+'a';
			name += ch;
		}
		name += " ";
		ch = rand()%(('Z'-'A')+1)+'A';
		name += ch;
		for(j = 0; j<rand()%10; j++){
			ch = rand()%('z'-'a')+'a';
			name += ch;
		}
		major = majorList[rand()%(majorList.size())];
		data.push_back(*(new Student()));
		data[data.size()-1].id = to_string(id);
		data[data.size()-1].name = name;
		data[data.size()-1].major = major;
	}
}

//test code

int main(){

	//vector<Student> data{ {"1","a","A"},{"2","b","B"},{"3","c","C"}};
	vector<Student> data;
	GenerateData(data);

	while(true){
		switch(Menu()){
			case 1: 
				Insert(data);
				break;
			case 2:
				Sort(data);
				break;
			case 3:
				Delete(data);
				break;	
			case 4: 
				Print(data);
				break;
			case 5:
				cout << "Exit program";
				exit(0);
		}
	}	

	
	return 0;
}

