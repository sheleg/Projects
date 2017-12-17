.model small
.code
start:
	mov ds,es:[2ch]
	mov bx,0

search:
	add bx,1
	mov al,[bx]
	cmp al,0
	jne search

	mov al,[bx+1]
	cmp al,1

	jne search

	mov di,-1
	add bx,2

total:
	add bx,1
	add di,1
	mov al,[bx]
	cmp al,'\'


	jne next
	mov si,bx
next:
	cmp al,0
	jne total

	inc si
	mov al,[si]
point:
	int 29h
 	inc si
	mov al,[si]
	cmp al,0
	jne point


	mov ah,01h
	int 21h

	mov ax,4c00h
	int 21h
end start
