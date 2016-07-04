
# Telescope

## Composition

Is hard!

## Mezzolens constructors

```haskell
iso :: (s -> a) -> (b -> t) -> Iso s t a b -- (done)

lens :: (s -> a) -> (b -> s -> t) -> Lens s t a b -- (done)

prism :: (s -> Either t a) -> (b -> t) -> Prism s t a b -- (done)

affineTraversal :: (s -> Either t a) -> (b -> s -> t) -> AffineTraversal s t a b

traversal :: (forall f. Applicative f => (a -> f b) -> s -> f t) -> Traversal s t a b

sec :: ((a -> b) -> (s -> t)) -> SEC s t a b
sec :: Optic[=>][s, t, a, b] -> SEC s t a b -- reduces to function?
```

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
