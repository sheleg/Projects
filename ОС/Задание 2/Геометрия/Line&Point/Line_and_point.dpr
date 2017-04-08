{ ќпределить лежит ли заданна€ точка слева или справа от пр€мой,
  котора€ задана своим направл€ющим вектором. }

program Line_and_point;

{$APPTYPE CONSOLE}

uses
  SysUtils, Windows;

var
   lbx, lby: Real;  { оординаты начала направл€ющего вектора}
   lex, ley: Real;  { оординаты конца направл€ющего вектора}
   px, py: Real;    { оординаты точки}

{‘ункци€ вычисл€ет скал€рное произведение вектора (lbx,lby)-(px,py) на
 вектор, перпендикул€рный направл€ющему вектору пр€мой.}
function Otkl: Real;
var
  nx, ny: Real; { оординаты вектора, перпендикул€рного направл€ющему вектору.}
begin
  nx := lby - ley;
  ny := lex - lbx;
  Otkl := nx * (px-lbx) + ny * (py - lby);
end;

{ ”становить cp1251: дл€ вывода русского текста на консоль. }
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
  Write('¬ведите координаты начала направл€ющего вектора пр€мой: ');
  ReadLn(lbx, lby);
  Write('¬ведите координаты конца направл€ющего вектора пр€мой: ');
  ReadLn(lex, ley);
  Write('¬ведите координаты точки: ');
  ReadLn(px, py);
  if Otkl = 0 then
    WriteLn('“очка лежит на пр€мой')
  else
    if Otkl > 0 then
      WriteLn('“очка лежит слева от пр€мой')
    else
      WriteLn('“очка лежит справа от пр€мой');
  Readln;
End.