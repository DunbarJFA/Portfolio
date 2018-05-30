TITLE PrnIntReverseSum (PrnIntReverseSum.asm)

;This program takes in a user - specified number of integers, then prints their sum along with a list of the
;entries in reversed order.
;Author: Jerod Dunbar
;Last update : 4/8/17


INCLUDE Irvine32.inc

.data
prompt1 BYTE "How many numbers would you like to enter?: ", 0
prompt2 BYTE "Enter number ", 0
prompt3 BYTE ": ", 0
reverse BYTE "These are your numbers in reverse: ", 0
sumPrompt BYTE "This is the sum of your entries: ", 0
count DWORD ?
promptCount DWORD 1
sum SDWORD ?

.code
main PROC
call WaitMsg
move edx, OFFSET prompt1 ;prompt user for number of entries
call WriteString ;print query to screen
call ReadInt ;read in user input
mov count, eax ;store input as count variable
mov ecx, count ;move value of count to ecx for looping

L1:
mov edx, OFFSET prompt2 ;print query
call WriteString ;WriteString prints to screen
mov eax, promptCount ;print promptCount
call WriteInt ;WriteInt prints to screen
mov edx, OFFSET prompt3 ;print colon
call WriteString ;WriteString prints to screen
call ReadInt ;get number
inc promptCount ;increment promptCount for accurate printing
add sum, eax ;add value to sum
push eax ;push on stack
Loop L1

//Pop each element from the stack and print them in
//reverse order along with the sum of the entries.

mov ecx, count ;refresh ecx for next loop
mov edx, reverse ;Prepare to print "reverse" string
call WriteString ;Print "reverse" string to screen

L2: 
pop eax ;pop the value of eax from the stack
call WriteInt ;print that popped value to the screen
Loop L2

mov edx, sumPrompt ;Prepare to print "sumPrompt"
call WriteString ;Print "sumPrompt" to screen
mov eax, sum ;Prepare to print sum
call WriteInt ;Print sum to screen


main ENDP /
END main /