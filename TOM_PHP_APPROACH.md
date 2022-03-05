# Introduce the indirection layer
- when refactoring, we often end up in situation where single change causing cascading effects of changes and we retreat in our corner and cry, and recently, I have learned a good trick from J.B. Rainberger to introduce indirection layer to help us making small steps incrementally
- a different approach from tomphp: https://www.youtube.com/watch?v=hGkdDxYCCKY&ab_channel=tomphp
- X minutes do
    - It seems like OrderLines is a good home (domain concept) for Order.lines field, once you extract Order.lines into OrderLines, we could see a whole bunch of behavior should belong into OrderLines as well
    - **Setup**
    - [ ] checkout https://github.com/tonytvo/introduce-indirection-layer
    - [ ] run tests, see them pass
    - **extract lines logic in class `Order` into methods**
      - [ ] Extract a method `isEmpty()` to check if the lines is empty 
        - ![extract isEmpty method](./snippets/extract-is-empty.gif) 
        - [ ] introduce parameter lines
          - ![introduce lines parameter](./snippets/introduce-lines-parameter-for-is-empty.gif)
      - [ ] Extract a method `asList()` to return the list of `OrderLine` 
        - ![extract asList method](./snippets/extract-aslist-lines.gif) 
        - [ ] introduce parameter lines
      - [ ] Extract a method `incrementItemQuantity(ItemAdded)`
        - ![extract incrementItemCount](./snippets/extract-increment-item-quantity.gif) 
        - [ ] introduce parameter lines
    - **move lines into parameter object `OrderLines`**
      - ...

# References
- https://github.com/codecop/dependency-breaking-katas
- https://tonytvo.github.io/what-simple-way-share-learn-refactor/
- https://github.com/tomphp/event-sourcing-example
