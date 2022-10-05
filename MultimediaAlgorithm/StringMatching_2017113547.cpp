#include <iostream>
#include <cstring>
#include <vector>
#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <chrono>

#define MAX_FILE_NAME_LEN 1024

#define ALPHABET_LEN 256
#define max(a, b) ((a < b) ? b : a)

#define BILLION 1000000000.0f

using namespace std;
using namespace std::chrono;

class MalPart{
public:
	char* fileName;
	vector<char> malCode;
	int malPosition;
	MalPart(char* fn, vector<char> mc, int mp){
		fileName = (char*)malloc(sizeof(fn));
		strncpy(fileName, fn, sizeof(fn));
		for(int i=0; i<mc.size(); i++){
			malCode.push_back(mc[i]);
		}
		malPosition = mp;
	}
};

vector<MalPart> malPartVec;

void AddMalPart(char* fileN, vector<char> malC, int malP){
	MalPart mp(fileN, malC, malP);
	malPartVec.push_back(mp);
}

void printMalPart(MalPart malPart){
	cout << malPart.fileName << "/";
	for(int i=0; i<malPart.malCode.size(); i++){
		cout << malPart.malCode[i];
	}
	cout << "/" << malPart.malPosition << endl;
}

void GenString(FILE* file, vector<char>& string){
	char ch;
	while( (ch = fgetc(file)) != EOF ){
		string.push_back(ch);
	}
	rewind(file);
}
void GetFile(vector<char *>& fileNameVec, vector< vector<char>* >& stringVec){
	cout << "Enter file names. Seperate with 'ENTER' key. To finish entering, enter 'q':"<<endl;
	char* fileName;
	int entryNum = 0;
	int len;
	FILE* fp;
	bool finishFlag = false;
	bool fileIsRedundant;
	vector<char>* _string;
	while(true){
		entryNum++;
		fileName = (char*)malloc(sizeof(char)*MAX_FILE_NAME_LEN);
		while(true){
			fileIsRedundant = false;
			cout << "Program number " << entryNum << ":";
			fgets(fileName, MAX_FILE_NAME_LEN, stdin);
			len = strlen(fileName);
			fileName[len-1] = '\0';
			for(int i=0; i<fileNameVec.size(); i++){
				if(strcmp(fileName, fileNameVec[i])==0){
					cout << "Redundant file. Enter again." << endl;
					fileIsRedundant = true;
					break;
				}
			}
			if(fileIsRedundant==true) continue;

			if(strcmp(fileName,"q")==0){
				if(fileNameVec.size()<15){
					cout << "Not enough file number. More than or equal to 15 files are needed. Enter again." << endl;
					continue;
				}
				free(fileName);
				finishFlag = true;
				break;
			}
			fp = fopen(fileName,"rt");
			if(fp == NULL){
				cout << "File opening is failure. File name should be checked. Enter again."<< endl;
				continue;
			}
			_string = new vector<char>();
			GenString(fp, *_string);
			if( _string->size() < 27 ){
				cout << "File has not enough characters. More than or equal to 27 characters. Enter again." << endl;
				free(_string);
				continue;
			}
			fclose(fp);
			fileNameVec.push_back(fileName);
			stringVec.push_back(_string);
			break;
		}
		if(finishFlag == true) break;
	}
}
void GetMalCode(vector<char>& malCode, int& patternLen){
	while(true){
		cout << "Enter malicious code: ";
		char ch;
		while( (ch = getchar()) != '\n'){
			malCode.push_back(ch);
		}
		if( malCode.size()<12 ){
			cout << "Malicious code has not enough characters. More than or equal to 12 characters. Enter again." << endl;
			malCode.clear();
			continue;
		}
		break;
	}
	while(true){
		cout << "Enter malicious code pattern length: ";
		cin >> patternLen;
		if(cin.fail()){
			cout << "Pattern length should be a number. Enter again." << endl;
			cin.clear();
			cin.ignore(1000, '\n');
			continue;
		}
		if( patternLen < 1 || patternLen > malCode.size() ){
			cout << "Pattern length should be more than 0 and less than or equal to malicious code length. Enter again." << endl;
			continue;
		}
		break;
	}
}
void GenMalPattern(vector<char> malCode, int patternLen, vector< vector<char>* >& patternVec){
	vector<char>* _pattern;
	for(int i = 0; i<= (malCode.size()-1)-patternLen+1; i++){
		_pattern = new vector<char>();
		for(int j=0; j<patternLen; j++){
			_pattern->push_back(malCode[i+j]);
		}
		patternVec.push_back(_pattern);
	}
}

// KMP Algorithm
void PRE_MP(vector<char> x, vector<int>& mp_next){
	int m = x.size();
	int i, j;
	i=0;
	j = mp_next[0] = -1;
	while(i<m){
		while(j>-1 && x[i] != x[j]) j = mp_next[j];
		mp_next[++i] = ++j;
	}
}

bool MP(vector<char> y, vector<char> x, char* fileName){
	int n = y.size();
	int m = x.size();
	int i, j;
       	vector<int> mp_next(m+1);
	PRE_MP(x, mp_next);
	
	bool isMal = false;

	i=j=0;
	while(i<n){
		while(j>-1 && x[j] != y[i]) j = mp_next[j];
		i++;
		j++;
		if(j>=m){
			isMal = true;
			//cout << "matched index: " << i-j << endl;
			j = mp_next[j];
			AddMalPart(fileName, x, i-j);
		}
	}
	return isMal;
}

// Upgraded KMP Algorithm
void PRE_KMP(vector<char> x, vector<int>& kmp_next){
	int m = x.size();
	int i, j;
	i=0;
	j = kmp_next[0] = -1;
	while(i<m){
		while(j>-1 && x[i] != x[j]) j = kmp_next[j];
		i++;
		j++;
		if(x[i] == x[j]) kmp_next[i] = kmp_next[j];
		else kmp_next[i] = j;
	}
}

bool KMP(vector<char> y, vector<char> x, char* fileName){
	int n = y.size();
	int m = x.size();
	int i, j;
       	vector<int> kmp_next(m+1);
	PRE_KMP(x, kmp_next);
	bool isMal = false;
	i=j=0;
	while(i<n){
		while(j>-1 && x[j] != y[i]) j = kmp_next[j];
		i++;
		j++;
		if(j>=m){
			isMal =  true;
			//cout << "matched index: " << i-j << endl;
			j = kmp_next[j];
			AddMalPart(fileName, x, i-j);
		}
	}
	return isMal;
}



//Boyer Moore Algorithm

// Bad Character
// shift distance array
// delta1[c] = (patlen-1) - (index of most right c)
// if c == string[i] and c !=pat[patlen-1],
// then i+=delta1[c]
// so, string[i] and c do line up;
// delta1[c] = min(shift distance)

// CAUTION
// if only one x == pat[patlen-1] or x is not in pat,
// then delta1[x] == patlen
// complexity == theta(ALPHABET_LEN+patlen)

void make_delta1(vector<int>& delta1, vector<char>& pat) {
	int patlen = pat.size();
	for (int i=0; i < ALPHABET_LEN; i++) {
		delta1[i] = patlen;
	}
	
	for (int i=0; i < patlen-1; i++) {
		delta1[pat[i]] = patlen-1 - i;
	}
}

// if (word[pos], ... ,word[wordlen-1]) == (word[0], ..., word[wordlen-1-pos])
// then return true
bool is_prefix(vector<char>& word, int pos) {
	int wordlen = word.size();
	int suffixlen = wordlen - pos;
    	// return ! strncmp(word, &word[pos], suffixlen);
    	for (int i = 0; i < suffixlen-1-(pos-1); i++) {
        	if (word[i] != word[pos+i]) {
            		return false;
        	}
    	}
    	return true;
}
// same code
/*
bool is_prefix(char* word, int wordlen, int pos) {
    int suffixlen = wordlen - pos;
    return !strncmp(word, &word[pos], suffixlen);
}
*/

// if (word[pos-(i-1)], ... ,word[pos]) == (word[wordlen-1-(i-1)], ... , word[wordlen-1])
// then return i;
// example
// suffix_length("dddbcabc", 8, 4) = 2

int suffix_length(vector<char>& word, int pos) {
	int wordlen = word.size();
    	int i;
    	// We increase i
    	// until wordlen-1-(i-1) == mismatch index
    	// or wordlen-1-(i-1) == 0
    	for (i = 0; (word[pos-i] == word[wordlen-1-i]) && (i <= pos); i++);
    	return i;
}

// Good Suffix
// shift distance array
// 
// when,
// at pat[pos],
// mismatch exist,
// we align like this:

// case 1:
// ( pat[pos+1], ... ,pat[patlen-1] )
// ==( pat[substring_start_index], ... ,pat[substring_start_index+((patlen-1)-pos)] )
// or
// case 2:
// ( pat[suffix_start_index], ... ,pat[patlen-1] )
// ==(pat[0],    ...  ,pat[patlen-1-(suffix_start_index-1)-1])

// In case 1,
// at or after mismatch,
// next possible match starts
// If,
// ( pat[pos+1], ... ,pat[patlen-1] )
// ==( pat[substring_start_index], ... ,pat[substring_start_index+((patlen-1)-pos)] )
// then, at mismatch index + (patlen-substring_start_index),
// next match starts

// CAUSION:
// If multiple substring exist,
// then longest one is picked

// If, in case 1, in pat, elsewhere,
// pat[pos+1], ... ,pat[patlen-1] does not exist
// or
// substring exist
// but, pat[pos] != pat[substring_start_index-1]
// then we do case 2

// In case 2,
// if, in pat, elsewhere, pat[pos+1], ... , pat[patlen-1] does not exist,
// then we are not looking at middle of a mach.
// But, we may be looking at left out side of a match.

// make_delta2 has 2 loops.
// first one is for case 2.
// second one is for case 1.
// first loop is like KMP algorithm.
// but first loop match suffix and prefix.
// this matching has bad case.
// let pat is "ABYXCDBYX" and text is ".....ABYXCDEYX",
// In BYX, at B(!=E), mismatch exist.
// But prefix "YX" does not exist.
// So, 9 characters is skipped. 
// BM and KMP is similar
// but KMP use fail link
// and BM does not use it.

void make_delta2(vector<int>& delta2, vector<char>& pat) {
	int patlen = pat.size();
    	delta2[patlen-1] = 1;
    
    	int p;
    	int last_prefix_index = patlen;

    	// first loop
    	for (p=patlen-2; p>=0; p--) {
        	if (is_prefix(pat, p+1)) {
            		last_prefix_index = p+1;
        	}
        	delta2[p] = patlen-(patlen-1-(last_prefix_index-1)) + (patlen-1 - p);
    	}
    

    	// second loop
    	for (p=0; p < patlen-1; p++) {
        	int slen = suffix_length(pat, p);
        	if (pat[p - slen] != pat[patlen-1 - slen]) {
            		delta2[patlen-1 - slen] = patlen-1 - p + slen;
        	}
    	}
}

// In string, if match occur, then return true. Else, return false
bool Boyer_moore (vector<char> string, vector<char> pat, char* fileName) {
	int stringlen = string.size();
	int patlen = pat.size();
    	vector<int> delta1(ALPHABET_LEN);
    	vector<int> delta2(patlen);
	make_delta1(delta1, pat);
    	make_delta2(delta2, pat);
	bool isMal = false;
    
    	int t, initial_jump;// if match exist, then we start with initial_jump
    	for(t=1; t<patlen; t++){
        	if(is_prefix(pat,t))break;
    	}
    	initial_jump = t;

    	if (patlen == 0) {
        	//printf("empty pattern\n!");
        	return false;
    	}

    	int i = patlen - 1;// string index
    	while (i < stringlen) {
        	int j = patlen - 1;// pat index
        	while (j >= 0 && (string[i] == pat[j])) {
            		--i;
            		--j;
        	}
        	if (j < 0) {
            		//printf("matched index:%d\n",(int)(i+1));
			AddMalPart(fileName, pat, i);
			isMal = true;
            		i++;
            		i+=patlen-1;
            		i+=initial_jump;
            		continue;
        	}
        	int shift = max(delta1[string[i]], delta2[j]);
        	i += shift;
    	}
	return isMal;
}

void Detect( 
		bool (* Algorithm)(vector<char>, vector<char>, char* fileName), 
		vector<char*>& fileNameVec, 
		vector< vector<char>* >& stringVec, 
		vector< vector<char>* >& patVec, 
		vector<char*>& normalCodeVec,
		vector<char*>& malCodeVec
		){
	bool flag;
	
	for(int i=0; i<stringVec.size(); i++){
		flag = false;
		for(int j=0; j<patVec.size(); j++){
			if( Algorithm( *(stringVec[i]), *(patVec[j]), fileNameVec[i] ) ){
				flag = true;
				break;
			}
		}
		if(flag==true) malCodeVec.push_back(fileNameVec[i]);
		else normalCodeVec.push_back(fileNameVec[i]);
	}
}
void Report(vector<char *> normalCodeVec, vector<char *> malCodeVec){
	cout << endl;
	cout << "-Normal Program List: " << endl;
	for(int i=0; i<normalCodeVec.size(); i++){
		cout << normalCodeVec[i] << endl;
	}
	cout << "Number of normal programs: "<< normalCodeVec.size() << endl;
	cout << "-Malicious Program List: " << endl;
	for(int i=0; i<malCodeVec.size(); i++){
		cout << malCodeVec[i] << endl;
	}
	cout << "Number of malicious programs: "<< malCodeVec.size() << endl;
	cout << endl;
}

int main(void){	
	vector<char*> fileNameVec;
	vector< vector<char>* > stringVec;
	GetFile(fileNameVec, stringVec);
	vector<char> malCode;
	int patternLen;
	GetMalCode(malCode, patternLen);
	vector< vector<char>* > patternVec;
	GenMalPattern(malCode, patternLen, patternVec);
	vector<char *> normalCodeVec;
	vector<char *> malCodeVec;
	double executionTimeLength = 0;
	
	cout << endl << "***KMP detection" << endl;
	high_resolution_clock::time_point start = high_resolution_clock::now();
	Detect(MP, fileNameVec, stringVec, patternVec, normalCodeVec, malCodeVec);
	high_resolution_clock::time_point end = high_resolution_clock::now();
	auto duration = duration_cast<nanoseconds>(end-start).count();

	Report(normalCodeVec, malCodeVec);
	cout << "Execution time length(seconds): "<< duration / BILLION<< endl;
	normalCodeVec.clear();
	malCodeVec.clear();
	
	cout << endl << "***Upgraded KMP detection" << endl;
	start = high_resolution_clock::now();
	Detect(KMP, fileNameVec, stringVec, patternVec, normalCodeVec, malCodeVec);
	end = high_resolution_clock::now();
	duration = duration_cast<nanoseconds>(end-start).count();
	
	Report(normalCodeVec, malCodeVec);
	cout << "Execution time length(seconds): "<< duration / BILLION<< endl;
	normalCodeVec.clear();
	malCodeVec.clear();

	cout << endl << "***Boyer Moore detection" << endl;
	start = high_resolution_clock::now();
	Detect(Boyer_moore, fileNameVec, stringVec, patternVec, normalCodeVec, malCodeVec);
	end = high_resolution_clock::now();
	duration = duration_cast<nanoseconds>(end-start).count();
	
	Report(normalCodeVec, malCodeVec);
	cout << "Execution time length(seconds): "<< duration / BILLION << endl;
	normalCodeVec.clear();
	malCodeVec.clear();
	
	for(int i=0; i<fileNameVec.size(); i++){
		free(fileNameVec[i]);
		free(stringVec[i]);
	}
	for(int i=0; i<patternVec.size(); i++){
		free(patternVec[i]);
	}
	
	//for(int i = 0; i<malPartVec.size(); i++){
	//	printMalPart(malPartVec[i]);
	//}
	return 0;
}
