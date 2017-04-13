{ ���������� ����� �� �������� ����� ����� ��� ������ �� ������,
  ������� ������ ����� ������������ ��������. }

program Line_and_point;

{$APPTYPE CONSOLE}

uses
  SysUtils, Windows;

var
   lbx, lby: Real;  {���������� ������ ������������� �������}
   lex, ley: Real;  {���������� ����� ������������� �������}
   px, py: Real;    {���������� �����}

{������� ��������� ��������� ������������ ������� (lbx,lby)-(px,py) ��
 ������, ���������������� ������������� ������� ������.}
function Otkl: Real;
var
  nx, ny: Real; {���������� �������, ����������������� ������������� �������.}
begin
  nx := lby - ley;
  ny := lex - lbx;
  Otkl := nx * (px-lbx) + ny * (py - lby);
end;

{ ���������� cp1251: ��� ������ �������� ������ �� �������. }
procedure SetCodePage1251;
begin
  if (SetConsoleCP(1251) = FALSE) or (SetConsoleOutputCP(1251) = FALSE) then
  begin
    Writeln('Can''t set console code page... Press <Enter> to close application...');
    Readln;
    Halt;
  end;
end;

Begin
  SetCodePage1251;
  Write('������� ���������� ������ ������������� ������� ������: ');
  ReadLn(lbx, lby);
  Write('������� ���������� ����� ������������� ������� ������: ');
  ReadLn(lex, ley);
  Write('������� ���������� �����: ');
  ReadLn(px, py);
  if Otkl = 0 then
    WriteLn('����� ����� �� ������')
  else
    if Otkl > 0 then
      WriteLn('����� ����� ����� �� ������')
    else
      WriteLn('����� ����� ������ �� ������');
  Readln;
End.