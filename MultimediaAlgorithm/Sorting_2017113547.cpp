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
		"불교학부", "영어영문학부", "일본학과", "중어중문학과", "철학과", "사학과", "신문방송학과", "수학과", "화학과", "통계학과", "물리반도체과학부",
		"법학과", "정치행정학부", "경제학과", "국제통상학과", "사회언론정보학부", "식품산업관리학과", "광고홍보학과", "사회복지학과", "경찰행정학부",
		"경영학과", "회계학과", "경영정보학과", "바이오환경과학과", "생명과학과", "식품생명공학과", "의생명공학과",
		"건설환경공학과", "건축학부", "기계로봇에너지공학과", "멀티미디어공학과", "산업시스템공학과", "융합에너지신소재공학과", "전자전기공학부",
		"컴퓨터정보통신공학부", "컴퓨터공학과", "정보통신공학전공", "화공생물공학과",
		"교육학과", "국어교육과", "역사교육과", "지리교육과", "수학교육과", "가정교육과", "체육교육과",
		"불교미술전공", "한국화전공", "서양화전공", "조소전공", "연극학부", "약학과", "융합보안학과", "사회복지상담학과", "글로벌무역학과"};
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

