TITLE CountWord(CountWord.asm)

;This program takes in a user - specified String, then prints the number of words in the string
;(filtering out excess whitespace).
;Author: Jerod Dunbar - Student ID: 2467186
;Last update : 4/3/2017

INCLUDE Irvine32.inc

.data
stringPrompt Byte "Please enter a string: ", 0
resultStart Byte "Your string contains ", 0
resultEnd Byte " words.", 0
signal Byte?
trimChar Byte 32
MAX = 80
userInput DWORD MAX+1 Dup(?)
wordCount DWORD 0


.code
main PROC
mov edx, OFFSET stringPrompt
Call WriteString
mov edx, OFFSET userInput
mov ecx, MAX
Call ReadString
mov ecx, eax
mov esi, 0

L1:

;Loop compares each character of the user-generated string to the ascii code for space (32)
;depending on the result, the program either jumps to IfChar to increment the word count, or 
;continues the original Loop (effectively skipping the character). 
;
;In charge of setting the signal variable when whitespace is detected. 

	cmp Byte PTR userInput[esi], 32     ;Test for whitespace at the character at PTR indicated by ESI
	jne IfChar                          ;If the character is not whitespace, jump to IfChar
	inc esi                             ;Increment the pointer
	mov signal, 1                       ;Set the signal variable to 1 to indicate that wordCount should be incremented
	Loop L1                             ;Loop L1 and decrement ECX
	jmp Result                          ;Unconditional jump to the Result label

IfChar: 

;Referenced by the main loop whenever a non-whitespace character is found (through CMP operation with the ascii value 32).
;This section is responsible for incrementing wordCount if the signal variable indicates that it is time to do so.

	cmp signal, 1                       ;Check the state of the signal variable 
	jne Skip                            ;If signal does not equal 1, jump to Skip label
	inc esi                             ;Increment the pointer
	inc wordCount                       ;Increment wordCount
	mov signal, 0                       ;Set the signal to 0 to indicate that wordCount should not be incremented with the next loop
	Loop L1                             ;Iterate the main loop

Skip:

;Referenced by the main loop whenever a character needs to be skipped. (I.E.: does not increment the word count)

	inc esi                             ;Increment the pointer
	Loop L1                             ;Iterate the main loop

Result:

;Jumped to upon main loop completion. Prints the results of the loop. 

	mov edx, OFFSET resultStart         ;Prep resultStart to be printed
	Call WriteString                    ;Print resultStart to screen
	mov eax, wordCount                  ;Prep wordCount to be printed
	Call WriteDec                       ;Print wordCount
	mov edx, OFFSET resultEnd           ;Prep resultEnd to be printed
	Call WriteString                    ;Print resultEnd
	Call WaitMsg                        ;Use WaitMsg to pause the program before ending

main ENDP
End main