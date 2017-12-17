.MODEL SMALL
.CODE
mov ds,es:[2CH]
mov di,4
mov dl,ds:[di]
mov ah,2
int 21h
mov ah,4CH
int 21h
end


