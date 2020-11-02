type proposition =
  False |
  True |
  Var of string |
  And of proposition * proposition |
  Or of proposition * proposition |
  Not of proposition |
  Imply of proposition * proposition |
  Equiv of proposition * proposition ;;

type conditional =
  IffyFalse |
  IffyTrue |
  IffyVar of string |
  If of conditional * conditional * conditional ;;

let rec ifify p =
match p with
True -> IffyTrue |
False -> IffyFalse |
Var (a) -> IffyVar(a) |
Not a -> If(ifify(a), IffyFalse, IffyTrue) |
And (a, b) -> If(ifify(a), ifify(b), IffyFalse) |
Or (a, b) -> If(ifify(a), IffyTrue, ifify(b)) |
Imply (a, b) -> If(ifify(a), ifify(b), IffyTrue) |
Equiv (a, b) -> If(ifify(a), ifify(b), If(ifify(b), IffyFalse, IffyTrue)) ;;


let rec normalize c =
let rec normalizing n a2 b2 =
match n with
If (n1, a1, b1) -> normalizing n1 (If(a1, a2, b2)) (If(b1, a2, b2)) |
_-> If(n, normalize a2, normalize b2) in
match c with
If (n, a , b) -> normalizing n a b |
_ -> c ;;


let substitute c v b =
let rec substituting p =
match p with
If(n, a, b) -> If(substituting n, substituting a, substituting b) |
m -> if p = v
then b
else m in
substituting c ;;


let rec simplify c =
match c with
If(n, a, b) ->
let a = simplify((substitute a) n IffyTrue) in
let b = simplify((substitute b) n IffyFalse) in
if n = IffyTrue
then a
else if n = IffyFalse
then b
else if b = IffyFalse && a = IffyTrue
then n
else if a = b
then a
else If(n, a, b) |
_ -> c ;;


let tautology p =
if simplify(normalize(ifify p)) = IffyTrue
then true
else false ;;


tautology ((Imply(Not(And(Var "p", Var "q")), Or(Not (Var "p"), Not (Var "q"))))) ;;
