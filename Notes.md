
# Telescope

## Composition

Is hard!

## Equality

We can restore the Equality type as

```haskell
type Equality s t a b = forall p. p a b -> p s t
```

Except there is not a common supertype to all typeclasses at all 
kinds (no `Any[_[_,_]`) so this will break all of the machinery.

## Traversal

These types may work and help to unify the story around Traversals.

```haskell
type Affine s t a b = 
  forall ~> . (Strong ~>, Choice ~>) => (a ~> b) -> (s ~> t)
  
class (Strong ~>, Choice ~>) => Wander ~> where
  wander :: Traversable f => (a ~> b) -> (f a ~> f b)
  
type Traversal s t a b =
  forall ~> . Wander ~> => (a ~> b) -> (s ~> t)
```

## References

- https://hackage.haskell.org/package/lens
- https://gist.github.com/sjoerdvisscher/7043326
- https://hackage.haskell.org/package/mezzolens
