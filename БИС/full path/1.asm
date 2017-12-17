.model small
.code
start:
	mov ds,es:[2ch]
	mov bx,0

total:
	add bx,1
	mov al,[bx]	
	int 29h	
	jmp total	

	mov ax,4c00h
	int 21h
end start