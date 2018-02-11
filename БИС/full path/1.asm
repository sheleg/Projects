.model small

.data
fullpath db 64 dup(?)

.code
start:
  push cs
  pop ds

  lea bp, fullpath

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
  inc di
	add bx,1
	mov al,[bx]
  mov [bp + di], al
  cmp al,0
	jne total

  ;mov byte ptr [bp + di], '$'

  push cs
  pop ds

  lea dx, fullpath
  mov ah, 41h
  int 21h

	mov ax,4c00h
	int 21h
end start
